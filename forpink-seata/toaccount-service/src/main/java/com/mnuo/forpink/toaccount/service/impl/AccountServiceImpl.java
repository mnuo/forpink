package com.mnuo.forpink.toaccount.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mnuo.forpink.toaccount.entity.TAccount;
import com.mnuo.forpink.toaccount.mapper.AccountMapper;
import com.mnuo.forpink.toaccount.service.AccountService;

import io.seata.rm.tcc.api.BusinessActionContext;
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
	  private AccountMapper accountMapper;

	  @Override
	  public boolean createOrder(BusinessActionContext actionContext, TAccount order) {
	    if(null == actionContext){
	      return false;
	    }
	    String xid = actionContext.getXid();
	    log.info("TccActionOne prepare, xid:" + xid);
	    accountMapper.insert(order);
	    return true;
	  }

	  @Override
	  public boolean commit(BusinessActionContext actionContext) {
	    String xid = actionContext.getXid();
	    log.info("TccActionOne commit, xid:" + xid);
	    return true;
	  }

	  @Override
	  public boolean rollback(BusinessActionContext actionContext) {
	    String xid = actionContext.getXid();
	    String orderNo = ((JSONObject) actionContext.getActionContext("order")).getString("orderNo");
	    accountMapper.deleteById(orderNo);
	    log.info("TccActionOne rollback, xid:" + xid);
	    return true;
	  }

}
