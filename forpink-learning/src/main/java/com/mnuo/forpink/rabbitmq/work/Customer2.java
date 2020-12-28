package com.mnuo.forpink.rabbitmq.work;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.mnuo.forpink.rabbitmq.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;

public class Customer2 {
	public final static String QUEUE_NAME = "work_queue";
	public static void main(String[] args) throws Exception {
		Connection connection = ConnectionUtil.getConnection();
		Channel channel = connection.createChannel();
		channel.queueDeclare(QUEUE_NAME, true, false, false, null);
		//同一时刻只发送1条消息给消费者(能者多劳,消费快的,会消费更多的消息)
		//保证在接收端一个消息没有处理完时不会接收另一个消息, 即消费者端发送了ack后才会接收下一个消息.
		//在这种情况下生产者端会尝试把消息发送给下一个空闲的消费者
		channel.basicQos(1);
		
		//声明队列的消费者o
		Consumer consumer = new DefaultConsumer(channel){
			@Override
			public void handleDelivery(String consumerTag, com.rabbitmq.client.Envelope envelope, com.rabbitmq.client.AMQP.BasicProperties properties, byte[] body) throws java.io.IOException {
				String message = new String(body, "UTF-8");
				System.out.println("customer2消费者: " + message);
				//手动返回结果
				channel.basicAck(envelope.getDeliveryTag(), false);
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			};
		};
		//定义的消费者监听队列autoAck: true自动返回结果,false手动返回结果
		channel.basicConsume(QUEUE_NAME, false, consumer);
	}
}
