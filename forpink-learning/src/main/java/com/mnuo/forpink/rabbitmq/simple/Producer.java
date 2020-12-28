package com.mnuo.forpink.rabbitmq.simple;

import com.mnuo.forpink.rabbitmq.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class Producer {
	private final static String QUEUE_NAME = "hello";
	
	public static void main(String[] args) throws Exception {
		Connection connection = ConnectionUtil.getConnection();
		Channel channel = connection.createChannel();
		//队列申请
		/*
         *   创建消息队列（如果有可以不用创建，但创建会覆盖之前的）
         *   第一参数：队列名称
         *   第二参数：队列是否持久化（存储到磁盘）
         *   第三参数：队列是否被独占
         *   第四参数：队列是否自动删除
         *   第五参数：
         */
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		
		String message = "simple queue hello world";
		/*
         *   发送消息
         *   第一参数：交换机名（简单模式不用交换机，但不能用null）
         *   第二参数：队列名称
         *   第三参数：
         *   第四参数：消息（字节流）
         *
         */
		channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
		channel.close();
		
		connection.close();
	}

}
