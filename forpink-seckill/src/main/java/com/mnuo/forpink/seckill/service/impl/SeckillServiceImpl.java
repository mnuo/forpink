package com.mnuo.forpink.seckill.service.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mnuo.forpink.seckill.common.Result;
import com.mnuo.forpink.seckill.common.aop.ServiceLock;
import com.mnuo.forpink.seckill.common.dynamicquery.DynamicQuery;
import com.mnuo.forpink.seckill.common.enums.SeckillStatEnum;
import com.mnuo.forpink.seckill.common.utils.Snowflake;
import com.mnuo.forpink.seckill.module.Seckill;
import com.mnuo.forpink.seckill.module.SuccessKilled;
import com.mnuo.forpink.seckill.respository.SeckillRepository;
import com.mnuo.forpink.seckill.service.IProxSeckillService;
import com.mnuo.forpink.seckill.service.ISeckillService;

import lombok.extern.slf4j.Slf4j;

@Service("seckillService")
@Slf4j
public class SeckillServiceImpl implements ISeckillService {
	private Lock lock = new ReentrantLock(true);//互斥锁 参数默认false，不公平锁
	@Autowired
	DynamicQuery dynamicQuery;
	
	@Autowired
	IProxSeckillService proxSeckillService;
	
	@Autowired
	SeckillRepository seckillRepository;
	@Override
	@Transactional
	public void deleteSeckill(long seckillId) {
		String nativeSql = "DELETE FROM  success_killed WHERE seckill_id=?";
		dynamicQuery.nativeExecuteUpdate(nativeSql, new Object[]{seckillId});
		nativeSql = "UPDATE seckill SET number =100 WHERE seckill_id=?";
		dynamicQuery.nativeExecuteUpdate(nativeSql, new Object[]{seckillId});
	}
	@Override
	@Transactional
	public Result startSeckill(long seckillId, long userId) {
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
			
			/**
             * 这里仅仅是分表而已，提供一种思路，供参考，测试的时候自行建表
             * 按照用户 ID 来做 hash分散订单数据。
             * 要扩容的时候，为了减少迁移的数据量，一般扩容是以倍数的形式增加。
             * 比如原来是8个库，扩容的时候，就要增加到16个库，再次扩容，就增加到32个库。
             * 这样迁移的数据量，就小很多了。
             * 这个问题不算很大问题，毕竟一次扩容，可以保证比较长的时间，而且使用倍数增加的方式，已经减少了数据迁移量。
             */
//            String table = "success_killed_"+userId%8;
//            nativeSql = "INSERT INTO "+table+" (seckill_id, user_id,state,create_time)VALUES(?,?,?,?)";
//            Object[] params = new Object[]{seckillId,userId,(short)0,createTime};
//            dynamicQuery.nativeExecuteUpdate(nativeSql,params);
			//支付
			return Result.ok(SeckillStatEnum.SUCCESS);
		}else{
			return Result.error(SeckillStatEnum.END);
		}
	}

	@Override
	public Long getSeckillCount(long seckillId) {
		String nativeSql = "SELECT count(*) FROM success_killed WHERE seckill_id=?";
		Object object =  dynamicQuery.nativeQueryObject(nativeSql, new Object[]{seckillId});
		return ((Number) object).longValue();
	}
	/**
	 * 如果锁是在事务内, 释放锁的时候, 事务没有提交,另一个事务就有可能造成不可重复读(脏读:针对未提交数据, 不可重复读: 针对其他提交前后，读取数据本身的对比, 幻读: 针对其他提交前后，读取数据条数的对比), 故需要将锁上移至事务外层.
	 * 但是在jpa中更新/新增操作需要事务, 并且spring中事务是基于代理, 在同一个类中, Propagation.REQUIRES_NEW不会在Spring中使用JPA创建新的事务
	 * (Propagation.REQUIRES_NEW does not create a new transaction in Spring with JPA)
	 * 需要使用代理 A --> proxy --> B 方有效
	 */
	@Override
	@Transactional
	public Result startSeckillLock(long seckillId, long userId) {
		log.info("userId" + userId + "开始购买.");
		lock.lock();
		try {
			return proxSeckillService.seckillLockOutOfTransaction(seckillId, userId);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
		//以下为synchronized
//		log.info("userId" + userId + "开始购买.");
//		//检验库存
//		String nativeSql = "SELECT number FROM seckill WHERE seckill_id=?";
//		Object object =  dynamicQuery.nativeQueryObject(nativeSql, new Object[]{seckillId});
//		Long number =  ((Number) object).longValue();
//		if(number > 0){
//			//扣钱库存
//			nativeSql = " UPDATE seckill  SET number=number-1 WHERE seckill_id=? ";
//			dynamicQuery.nativeExecuteUpdate(nativeSql, new Object[]{seckillId});
//			//生成订单
//			SuccessKilled killed = new SuccessKilled();
//			killed.setSuccessKillId(new Date().getTime());
//			killed.setSeckillId(seckillId);
//			killed.setUserId(userId);
//			killed.setState((short)0);
//			Timestamp createTime = new Timestamp(new Date().getTime());
//			killed.setCreateTime(createTime);
//			dynamicQuery.save(killed);
//		}else{
//			return Result.error(SeckillStatEnum.END);
//		}
		return Result.ok(SeckillStatEnum.SUCCESS);
	}
	@Override
	@Transactional
	@ServiceLock
	public Result startSeckillAoplock(long seckillId, long userId) {
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
	/**
	 * 数据库悲观锁, 如果限流可能出现少卖现象
	 */
	@Override
//	@ServiceLimit
	@Transactional
	public Result startSeckillDBPCC_ONE(long seckillId, long userId) {
		log.info("userId" + userId + "开始购买.");
		//检验库存
		String nativeSql = "SELECT number FROM seckill WHERE seckill_id=? FOR UPDATE";
		Object object =  dynamicQuery.nativeQueryObject(nativeSql, new Object[]{seckillId});
		Long number =  ((Number) object).longValue();
		if(number > 0){
			//扣钱库存
			nativeSql = " UPDATE seckill  SET number=number-1 WHERE seckill_id=? ";
			dynamicQuery.nativeExecuteUpdate(nativeSql, new Object[]{seckillId});
			//生成订单
			SuccessKilled killed = new SuccessKilled();
			Snowflake idWorker = new Snowflake(0, 0);
	        long id = idWorker.nextId();
			killed.setSuccessKillId(id);
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
	/**
     * SHOW STATUS LIKE 'innodb_row_lock%'; 
     * 如果发现锁争用比较严重，如InnoDB_row_lock_waits和InnoDB_row_lock_time_avg的值比较高
     */
	@Override
	@Transactional
	public Result startSeckilDBPCC_TWO(long seckillId, long userId) {
		//单用户抢购一件商品没有问题、但是抢购多件商品不建议这种写法
		String nativeSql = "UPDATE seckill  SET number=number-1 WHERE seckill_id=? AND number>0";//UPDATE锁表
		int count = dynamicQuery.nativeExecuteUpdate(nativeSql, new Object[]{seckillId});
		if(count>0){
			SuccessKilled killed = new SuccessKilled();
			Snowflake idWorker = new Snowflake(0, 0);
	        long id = idWorker.nextId();
	        killed.setSuccessKillId(id);
			killed.setSeckillId(seckillId);
			killed.setUserId(userId);
			killed.setState((short)0);
			killed.setCreateTime(new Timestamp(new Date().getTime()));
			dynamicQuery.save(killed);
			return Result.ok(SeckillStatEnum.SUCCESS);
		}else{
			return Result.error(SeckillStatEnum.END);
		}
	}
	@Override
	@Transactional
	public Result startSeckilDBOCC(long seckillId, long userId, int i) {
		Optional<Seckill> kill = seckillRepository.findById(seckillId);
		
		if(kill.isPresent() && kill.get().getNumber() > 0){
			log.info("log");
			//乐观锁
			String nativeSql = "UPDATE seckill  SET number=number-?,version=version+1 WHERE seckill_id=? AND version = ?";
			int count = dynamicQuery.nativeExecuteUpdate(nativeSql, new Object[]{i,seckillId,kill.get().getVersion()});
			if(count>0){
				SuccessKilled killed = new SuccessKilled();
				Snowflake idWorker = new Snowflake(0, 0);
		        long id = idWorker.nextId();
		        killed.setSuccessKillId(id);
				killed.setSeckillId(seckillId);
				killed.setUserId(userId);
				killed.setState((short)0);
				killed.setCreateTime(new Timestamp(new Date().getTime()));
				dynamicQuery.save(killed);
				return Result.ok(SeckillStatEnum.SUCCESS);
			}else{
				return Result.error(SeckillStatEnum.END);
			}
		}else{
			return Result.error(SeckillStatEnum.END);
		}
	}
}


