package com.mnuo.forpink.rabbitmq.routing;

import java.io.IOException;

import com.mnuo.forpink.rabbitmq.ConnectionUtil;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class Customer1 {
	private final static String EXCHANGE_NAME = "routing_exchange";
	private final static String QUEUE_NAME = "routing_queue1";
	
	public static void main(String[] args) throws Exception {
		Connection connect = ConnectionUtil.getConnection();
		Channel channel = connect.createChannel();
		//声明交换机
		channel.exchangeDeclare(EXCHANGE_NAME, "direct");
		//声明队列
		channel.queueDeclare(QUEUE_NAME, true, false, false, null);
		//队列绑定到交换机
		channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "info");
		channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "warning");
		
		//同一时刻只发送1条消息给消费者(能者多劳,消费快的,会消费更多的消息)
		//保证在接收端一个消息没有处理完时不会接收另一个消息, 即消费者端发送了ack后才会接收下一个消息.
		//在这种情况下生产者端会尝试把消息发送给下一个空闲的消费者
		channel.basicQos(1);
		
		Consumer consumer = new DefaultConsumer(channel){
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body)
					throws IOException {
				String message = new String(body, "UTF-8");
				System.out.println("路由模式 消费者1 消息: " + message);
				channel.basicAck(envelope.getDeliveryTag(), false);
			}
		};
		
		channel.basicConsume(QUEUE_NAME, false, consumer);
	}

}
