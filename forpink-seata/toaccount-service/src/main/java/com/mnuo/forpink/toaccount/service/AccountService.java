package com.mnuo.forpink.toaccount.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mnuo.forpink.toaccount.entity.TAccount;

import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.BusinessActionContextParameter;
import io.seata.rm.tcc.api.LocalTCC;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;

/**
 * <p>
 * 装车单 服务类
 * </p>
 *
 * @author hanj
 * @since 2020-06-08
 */
@LocalTCC
public interface AccountService extends IService<TAccount> {
	/**
	   * Prepare boolean.
	   *
	   * @param actionContext the action context
	   * @param order             the a
	   * @return the boolean
	   */
	  @TwoPhaseBusinessAction(name = "TccActionOne" , commitMethod = "commit", rollbackMethod = "rollback")
	  boolean createOrder(BusinessActionContext actionContext,@BusinessActionContextParameter(paramName = "account") TAccount order);

	  /**
	   * Commit boolean.
	   *
	   * @param actionContext the action context
	   * @return the boolean
	   */
	  boolean commit(BusinessActionContext actionContext);

	  /**
	   * Rollback boolean.
	   *
	   * @param actionContext the action context
	   * @return the boolean
	   */
	  boolean rollback(BusinessActionContext actionContext);
}
