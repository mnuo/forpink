package com.mnuo.forpink.rabbitmq.work;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.mnuo.forpink.rabbitmq.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * 工作模式, 单发多接受
 * @author administrator
 */
public class Producer {
	
	private final static String QUEUE_NAME= "work_queue";
	
	public static void main(String[] args) throws IOException, TimeoutException {
		//获取连接
		Connection connection = ConnectionUtil.getConnection();
		//声明信道
		Channel channel = connection.createChannel();
		
		//队列申明, duration=true消息持久化
		channel.queueDeclare(QUEUE_NAME, true, false, false, null);
		
		String message = getMessage(args);
		for (int i = 0; i < 20; i++) {
			channel.basicPublish("", QUEUE_NAME, null, (i + message).getBytes());
		}
		channel.close();
		connection.close();
	}

	private static String getMessage(String[] args) {
		if(args.length > 1){
			return "Hello Word!";
		}
		return joinstring(args, "");
	}

	private static String joinstring(String[] args, String string) {
		int length = args.length;
		if(length == 0) return "";
		StringBuilder words = new StringBuilder(args[0]);
		for (int i = 0; i < length; i++) {
			words.append(string).append(args[i]);
		}
		return words.toString();
	}
}
