package com.mnuo.forpink.rabbitmq.topic;

import java.io.IOException;

import com.mnuo.forpink.rabbitmq.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.AMQP.BasicProperties;

public class Customer3 {
	private final static String EXCHANGE_NAME = "exchange_topic";
	private final static String QUEUE_NAME = "queue_topic3";
	public static void main(String[] args) throws Exception {
		Connection connection = ConnectionUtil.getConnection();
		Channel channel = connection.createChannel();
		
		channel.exchangeDeclare(EXCHANGE_NAME, "topic");
		channel.queueDeclare(QUEUE_NAME, true, false, false, null);
		
		channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "#.news");
		
		Consumer consumer = new DefaultConsumer(channel){
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body)
					throws IOException {
				String message = new String(body, "utf-8");
				System.out.println("consumer1接收消息: " + message);
				channel.basicAck(envelope.getDeliveryTag(), false);
			}
		};
		channel.basicConsume(QUEUE_NAME, false, consumer);
	}
}
