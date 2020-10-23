package com.mnuo.order.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.Map;

//@FeignClient(value = "account-service")
@FeignClient(name = "account-service", path = "/account-service")
public interface AccountService {

    /**
     * 扣减账户余额
     */
    @RequestMapping("/account/decrease")
    Map<String, String> decrease(@RequestParam("userId") Long userId, @RequestParam("money") BigDecimal money);
}
