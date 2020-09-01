package com.mnuo.forpink.framework.controller;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mnuo.forpink.framework.common.Result;
import com.mnuo.forpink.framework.service.IUserSerivce;
import com.mnuo.forpink.framework.utils.Assert;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Api(tags ="秒杀")
@RestController
@RequestMapping("/seckill")
@Slf4j
public class SeckillController {
	private static int corePoolSize = Runtime.getRuntime().availableProcessors();
	//创建线程池  调整队列数 拒绝服务
	private static ThreadPoolExecutor executor  = new ThreadPoolExecutor(corePoolSize, corePoolSize+1, 10l, TimeUnit.SECONDS,
			new LinkedBlockingQueue<>(10000));
	
	@Autowired
	IUserSerivce userService;
	
	@ApiOperation(value="一(最low实现), 线程共享没有加锁")
	@PostMapping("/start")
	public Result start(long seckillId){
		log.debug("用户:{}{}","abc","meassage");
		Object users = userService.findAll();
		Assert.throwException("张家港");
		return Result.data(users);
	}
}
