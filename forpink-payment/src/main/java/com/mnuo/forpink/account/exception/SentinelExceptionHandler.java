package com.mnuo.forpink.account.exception;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.mnuo.forpink.account.pojo.Balance;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SentinelExceptionHandler {
	public static Balance blockExceptionHandle(Integer id, BlockException e){
		e.printStackTrace();
		log.error(e.getMessage(), e);
		return new Balance(0,0,0,"sentinel限流处理.");
		
	}
}
