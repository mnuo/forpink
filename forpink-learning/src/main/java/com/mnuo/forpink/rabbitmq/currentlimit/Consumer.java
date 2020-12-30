package com.mnuo.forpink.rabbitmq.currentlimit;

import java.io.IOException;

import com.mnuo.forpink.rabbitmq.ConnectionUtil;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.AMQP.BasicProperties;

public class Consumer {
	private final static String EXCHANGE_NAME = "qos_exchange";
	private final static String ROUTING_KEY = "qos.#";
	private final static String QUEUE_NAME = "qos_queue";
	
	public static void main(String[] args) throws Exception {
		Connection connection = ConnectionUtil.getConnection();
		Channel channel = connection.createChannel();
		
		channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC, true);
		channel.queueDeclare(QUEUE_NAME, true, false, false, null);
		channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, ROUTING_KEY);
		
		DefaultConsumer consumer = new DefaultConsumer(channel){
			
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body)
					throws IOException {
				System.out.println("MESSAGE: " + new String(body));
				channel.basicAck(envelope.getDeliveryTag(), false);//不批量签收
			}
		};
		/**
         *  prefetchSize: 0 不限制消息大小
         *  prefetchCount: 一次处理消息的个数, ack后继续推送
         *  global: false 应用在consumer级别
         */
        channel.basicQos(0, 1, false);
        //限流:autoAck需设置为false, 关闭自动签收
        channel.basicConsume(QUEUE_NAME, false, consumer);
	}
}
