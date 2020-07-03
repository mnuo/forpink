package com.mnuo.forpink.payment.service;

import org.springframework.stereotype.Component;

import com.mnuo.forpink.account.pojo.Balance;

@Component
public class BalanceServiceFallback implements BalanceService {
    @Override
    public Balance getBalance(Integer id) {
        return new Balance(0, 0, 0, "降级");
    }

}