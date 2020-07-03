package com.mnuo.forpink.seckill.service.impl;

import java.sql.Timestamp;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mnuo.forpink.seckill.common.Result;
import com.mnuo.forpink.seckill.common.dynamicquery.DynamicQuery;
import com.mnuo.forpink.seckill.common.enums.SeckillStatEnum;
import com.mnuo.forpink.seckill.module.SuccessKilled;
import com.mnuo.forpink.seckill.service.IProxSeckillService;

import lombok.extern.slf4j.Slf4j;

@Service("proxSeckillService")
@Slf4j
public class ProxSeckillServiceImpl implements IProxSeckillService {
	@Autowired
	DynamicQuery dynamicQuery;
	
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public Result seckillLockOutOfTransaction(long seckillId, long userId){
		log.info("userId" + userId + "开始购买.");
		//检验库存
		String nativeSql = "SELECT number FROM seckill WHERE seckill_id=?";
		Object object =  dynamicQuery.nativeQueryObject(nativeSql, new Object[]{seckillId});
		Long number =  ((Number) object).longValue();
		if(number > 0){
			//扣钱库存
			nativeSql = " UPDATE seckill  SET number=number-1 WHERE seckill_id=? ";
			dynamicQuery.nativeExecuteUpdate(nativeSql, new Object[]{seckillId});
			//生成订单
			SuccessKilled killed = new SuccessKilled();
			killed.setSuccessKillId(new Date().getTime());
			killed.setSeckillId(seckillId);
			killed.setUserId(userId);
			killed.setState((short)0);
			Timestamp createTime = new Timestamp(new Date().getTime());
			killed.setCreateTime(createTime);
			dynamicQuery.save(killed);
			return Result.ok(SeckillStatEnum.SUCCESS);
		}else{
			return Result.error(SeckillStatEnum.END);
		}
	}
}
