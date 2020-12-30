package com.mnuo.forpink.rabbitmq.dlx;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.mnuo.forpink.rabbitmq.ConnectionUtil;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.AMQP.BasicProperties;
/**
 * 当消息在队列中变成死信时，能被重新publish到另一个Exchange，该Exchange就是DLX。
	发生死信队列的情况：
	消息被拒绝（basic.reject/ basic.nack）并且requeue=false（没有重回队列）
	消息TTL过期
	队列达到最大长度
 * @author administrator
 */
public class DlxConsumer {
	private final static String EXCHANGE_NAME = "dlx_exchange";
	private final static String ROUTING_KEY = "dlx.#";
	private final static String QUEUE_NAME = "dlx_queue";
	
	
	private final static String DLX_EXCHANGE = "dlx.exchange";
	private final static String DLX_QUEUE = "dlx.queue";
	private final static String DLX_ROUTING_KEY = "#";
	
	public static void main(String[] args) throws Exception {
		Connection connection = ConnectionUtil.getConnection();
		Channel channel = connection.createChannel();
		
		channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
		//设置死信队列参数
		Map<String, Object> arguments = new HashMap<>();
		arguments.put("x-deal-letter-exchange", DLX_EXCHANGE);
		
		channel.queueDeclare(QUEUE_NAME, true, false, false, arguments);
		channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, ROUTING_KEY);
		
		//声明死信队列
		channel.exchangeDeclare(DLX_EXCHANGE, BuiltinExchangeType.TOPIC, true, false, null);
		channel.queueDeclare(DLX_QUEUE, true, false, false, null);
		channel.queueBind(DLX_QUEUE, DLX_EXCHANGE, DLX_ROUTING_KEY);
		
		DefaultConsumer consumer = new DefaultConsumer(channel){
			
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body)
					throws IOException {
				System.out.println("receive message : " + new String(body));
				channel.basicAck(envelope.getDeliveryTag(), false);
			}
		};
		channel.basicConsume(QUEUE_NAME, false, consumer);
	}
}
