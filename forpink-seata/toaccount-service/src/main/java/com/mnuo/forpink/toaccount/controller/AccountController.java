package com.mnuo.forpink.toaccount.controller;


import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mnuo.forpink.toaccount.entity.TAccount;

import io.swagger.annotations.Api;

/**
 * <p>
 * 派车单 前端控制器
 * </p>
 *
 * @author hanj
 * @since 2020-06-08
 */
@RestController
@RequestMapping("/account")
@Api(tags = "1 account")
public class AccountController {
	
	@Autowired
	com.mnuo.forpink.toaccount.service.AccountService accountService;

    /**
     * 扣减账户余额
     */
    @RequestMapping("/add/money")
    public Map<String,String> decrease(@RequestParam("userId") Long userId, @RequestParam("money") BigDecimal money){
        accountService.createOrder(null, new TAccount());
        Map<String, String> map = new HashMap<>();
        map.put("code", "200");
        map.put("msg", "扣减账户余额成功！");
        return map;
    }
}

