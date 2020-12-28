package com.mnuo.forpink.rabbitmq.routing;

import java.io.IOException;

import com.mnuo.forpink.rabbitmq.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * 生产者按routing key发送消息，不同的消费者端按不同的routing key接收消息。
 * @author administrator
 */
public class Producer {
	private final static String EXCHANGE_NAME = "routing_exchange";
	
	public static void main(String[] args) throws Exception {
		Connection connection = ConnectionUtil.getConnection();
		Channel channel = connection.createChannel();
		//声明交换机的类型为direct
		channel.exchangeDeclare(EXCHANGE_NAME, "direct");
		//发布消息3种消息routingKey的消息
		String message = "hello world";
		channel.basicPublish(EXCHANGE_NAME, "info", null, message.getBytes());
		System.out.println("路由模式发布info消息: " + message);
		
		message = "hello warning";
		channel.basicPublish(EXCHANGE_NAME, "warning", null, message.getBytes());
		System.out.println("路由模式发布warning消息: " + message);
		
		message = "hello error";
		channel.basicPublish(EXCHANGE_NAME, "error", null, message.getBytes());
		System.out.println("路由模式发布error消息: " + message);
		
	}
}
