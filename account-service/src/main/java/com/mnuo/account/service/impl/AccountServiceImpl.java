package com.mnuo.account.service.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mnuo.account.entity.TAccount;
import com.mnuo.account.mapper.AccountMapper;
import com.mnuo.account.service.AccountService;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 装车单 服务实现类
 * </p>
 *
 * @author hanj
 * @since 2020-06-08
 */
@Service
@Slf4j
//@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class AccountServiceImpl extends ServiceImpl<AccountMapper, TAccount> implements AccountService {
	@Autowired
	AccountMapper accountMapper;

	@Override
	public void decrease(Long userId, BigDecimal money){
	   log.info("------->account-service中扣减账户余额开始");
	   //模拟超时异常，全局事务回滚
//	        try {
//	            Thread.sleep(30*1000);
//	        } catch (InterruptedException e) {
//	            e.printStackTrace();
//	            
//	        }
	   BigDecimal.ZERO.divide(BigDecimal.ZERO);
	   accountMapper.decrease(userId,money);
       log.info("------->account-service中扣减账户余额结束");
	}

}
