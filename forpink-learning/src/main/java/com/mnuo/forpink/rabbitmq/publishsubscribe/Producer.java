package com.mnuo.forpink.rabbitmq.publishsubscribe;

import com.mnuo.forpink.rabbitmq.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class Producer {
	public final static String EXCHANGE_NAME = "publishSubsribe_exchange";
	public static void main(String[] args) throws Exception {
		Connection connection = ConnectionUtil.getConnection();
		Channel channel = connection.createChannel();
		//声明交换机EXCHANGE类型为fanout
		channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
		
		String message = "publish/subscrible hello world";
		channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());
		System.out.println("发布订阅者,发布消息:" + message);
		channel.close();
		connection.close();
	}
}
