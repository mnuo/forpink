package com.mnuo.forpink.rabbitmq.currentlimit;

import java.io.IOException;

import com.mnuo.forpink.rabbitmq.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * 限流: 
 *  RabbitMQ提供了一种Qos（服务质量保证）功能，
 *  即在非自动确认前提下，如果一定数目的消息未被确认前（通过consume或者channel设置Qos值），不进行消费新消息。
 *  限流需要设置channel.basicQos(0, 1, false);
	关闭autoAck，且需要手动签收。
	在重写的handleDelivery方法中，如果没有进行手动签收channel.basicAck()，
	那么消费端在接收消息时，因为prefetchCount设置为1，只会接收1条消息，剩下的消息的等待中，并不会被推送，直到手动ack后。
 * @author administrator
 */
public class Producer {
	private final static String EXCHANGE_NAME = "qos_exchange";
	private final static String ROUTING_KEY = "qos.key";
	
	public static void main(String[] args) throws Exception {
		Connection connection = ConnectionUtil.getConnection();
		Channel channel = connection.createChannel();
		
		String message = "send message of QOS DEMO";
		for (int i = 0; i < 10; i++) {
			channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, null, (i + message).getBytes());
		}
		
	}

}
