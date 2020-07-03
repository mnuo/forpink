package com.mnuo.forpink.account.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.csp.sentinel.EntryType;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.mnuo.forpink.account.exception.SentinelExceptionHandler;
import com.mnuo.forpink.account.pojo.Balance;

@RestController
@RefreshScope
public class PaymentController {
	@Value("${sleep:0}")
	private int sleep;
	
	@SuppressWarnings({ "unchecked", "rawtypes", "serial" })
	final static Map<String, Balance> balanceMap = new HashMap(){{
		put("1", new Balance(1, 10, 1000));
		put("2", new Balance(2, 20, 2000));
		put("3", new Balance(3, 30, 3000));
	}};
	
	@GetMapping("/pay/balance")
	@SentinelResource(value = "balance", entryType = EntryType.OUT, blockHandlerClass = SentinelExceptionHandler.class, blockHandler = "blockExceptionHandle")
	public Balance balance(Integer id){
		System.out.println("request: /pay/balance?id=" + id + ", sleep: " + sleep);
        if(sleep > 0) {
            try {
                Thread.sleep(sleep);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if(id != null && balanceMap.containsKey(id+"")) {
            return balanceMap.get(id+"");
        }
        return new Balance(0, 0, 0);
	}
	
	
}
