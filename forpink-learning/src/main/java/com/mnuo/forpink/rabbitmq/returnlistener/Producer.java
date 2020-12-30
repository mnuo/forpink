package com.mnuo.forpink.rabbitmq.returnlistener;

import java.io.IOException;

import com.mnuo.forpink.rabbitmq.ConnectionUtil;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ReturnListener;
/**
 * return 机制, 即发送数据不可达
 * @author administrator
 */
public class Producer {
	private final static String EXCHANGE_NAME="return_exchange";
	private final static String ROUTING_KEY = "return.key";
	private final static String ROUTING_KEY_ERROR = "wrong.key";
	
	public static void main(String[] args) throws Exception {
		Connection connection = ConnectionUtil.getConnection();
		
		Channel channel = connection.createChannel();
		
		String message = "send message of return demo";
		
		channel.addReturnListener(new ReturnListener() {
			@Override
			public void handleReturn(int replyCode, String replyText, String exchange, String routingKey, BasicProperties properties, byte[] body)
					throws IOException {
				System.err.println("============ handleReturn ============");
                System.err.println("replyCode —— " + replyCode);
                System.err.println("replyText —— " + replyText);
                System.err.println("exchange —— " + exchange);
                System.err.println("routingKey —— " + routingKey);
                System.err.println("properties —— " + properties);
                System.err.println("body —— " + new String(body));
			}
		});
		
		channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, true, null, message.getBytes());
		channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY_ERROR, true, null, message.getBytes());
	}
}
