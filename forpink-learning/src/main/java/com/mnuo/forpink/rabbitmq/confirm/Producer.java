package com.mnuo.forpink.rabbitmq.confirm;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.mnuo.forpink.rabbitmq.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;

/**
 * confirm确认机制
 * @author administrator
 */
public class Producer {
	private final static String EXCHANGE_NAME="confirm_exchange";
	private final static String ROUTING_KEY = "confirm.key";
	
	public static void main(String[] args) throws Exception, TimeoutException {
		Connection connection = ConnectionUtil.getConnection();
		Channel channel = connection.createChannel();
		
		//指定消息的投递模式: 确认模式
		channel.confirmSelect();
		
		//发送消息
		String msg = "Send message of confirm demo";
		channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, null, msg.getBytes());
		
		//添加确认监听
		channel.addConfirmListener(new ConfirmListener() {
			// 失败
			@Override
			public void handleNack(long deliveryTag, boolean multiple) throws IOException {
				System.out.println("失败了");
			}
			//成功
			@Override
			public void handleAck(long deliveryTag, boolean multiple) throws IOException {
				System.out.println("成功!");
			}
		});
	}
}
