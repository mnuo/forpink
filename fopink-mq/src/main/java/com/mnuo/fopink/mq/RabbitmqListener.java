package com.mnuo.fopink.mq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitmqListener {

	@RabbitListener(queues = "one")
	public void listerQueueOne(String message){
		System.out.print("queueOne" + message);
	}

	@RabbitListener(queues = "two")
	public void listerQueueTwo(String message){
		System.out.print("queueTwo" + message);
	}

}
