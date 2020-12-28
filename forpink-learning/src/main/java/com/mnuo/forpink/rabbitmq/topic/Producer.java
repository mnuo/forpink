package com.mnuo.forpink.rabbitmq.topic;

import com.mnuo.forpink.rabbitmq.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class Producer {
	private final static String EXCHANGE_NAME = "exchange_topic";
	public static void main(String[] args) throws Exception {
		Connection connection = ConnectionUtil.getConnection();
		Channel channel = connection.createChannel();
		channel.exchangeDeclare(EXCHANGE_NAME, "topic");
		
		String message = "发布了一条中国新闻消息";
		channel.basicPublish(EXCHANGE_NAME, "china.new", null, message.getBytes());
		System.out.println("发布消息china.new: " + message);
		
		message = "发布了一条中国天气消息";
		channel.basicPublish(EXCHANGE_NAME, "china.weather", null, message.getBytes());
		System.out.println("发布消息china.weather: " + message);
		
		message = "发布了一条美国新闻消息";
		channel.basicPublish(EXCHANGE_NAME, "usa.new", null, message.getBytes());
		System.out.println("发布消息usa.new: " + message);
		
		message = "发布了一条美国天气消息";
		channel.basicPublish(EXCHANGE_NAME, "usa.weather", null, message.getBytes());
		System.out.println("发布消息usa.weather: " + message);
	}
}
