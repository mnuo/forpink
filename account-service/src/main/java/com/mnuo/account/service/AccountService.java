package com.mnuo.account.service;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mnuo.account.entity.TAccount;

/**
 * <p>
 * 装车单 服务类
 * </p>
 *
 * @author hanj
 * @since 2020-06-08
 */
public interface AccountService extends IService<TAccount> {
	/**
     * 扣减账户余额
     * @param userId 用户id
     * @param money 金额
     */
    void decrease(Long userId, BigDecimal money);
}
