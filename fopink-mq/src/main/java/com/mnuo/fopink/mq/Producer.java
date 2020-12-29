package com.mnuo.fopink.mq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/producer/")
@Api(tags="测试")
public class Producer {
	
	@Autowired
	RabbitTemplate rabbitTemplate;
	@GetMapping("send")
	public String sendmessage(String message) {
		rabbitTemplate.convertAndSend("direct_exchange", "one", "value: " + message);
		return null;
	}
}
