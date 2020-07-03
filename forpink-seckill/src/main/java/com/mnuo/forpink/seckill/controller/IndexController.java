package com.mnuo.forpink.seckill.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mnuo.forpink.seckill.common.Result;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Api(value ="测试swagger-ui2")
@Controller
@Slf4j
@RequestMapping("/testswagger")
public class IndexController {
	@GetMapping("/start")
	@ApiOperation(value="生成静态商品页",nickname="科帮网")
	@ApiImplicitParams({
	})
	public Result start(){
		log.info("生成秒杀活动静态页");
		return Result.ok("你终于成功了.");
	}
	@GetMapping("/index")
	@ApiOperation(value="生成静态商品页",nickname="科帮网")
	@ApiImplicitParams({
	})
	public String index(){
		log.info("生成秒杀活动静态页");
		return "/index";
	}
}
