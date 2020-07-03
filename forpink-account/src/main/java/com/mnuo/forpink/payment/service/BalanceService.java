package com.mnuo.forpink.payment.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.mnuo.forpink.account.pojo.Balance;

@FeignClient(name="payment-server", fallback = BalanceServiceFallback.class)
public interface BalanceService {
	@RequestMapping(value = "/pay/balance", method = RequestMethod.GET)
    Balance getBalance(@RequestParam("id") Integer id);
}
