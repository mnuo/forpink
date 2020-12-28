package com.mnuo.forpink.rabbitmq;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class ConnectionUtil {
	public static Connection getConnection() throws IOException, TimeoutException{
		//定义连接池
		ConnectionFactory connectionFactory = new ConnectionFactory();
		//连接地址
		connectionFactory.setHost("127.0.0.1");
		connectionFactory.setPort(5672);
		connectionFactory.setUsername("guest");
		connectionFactory.setPassword("guest");
		//通过工厂类获取连接
		Connection connection = connectionFactory.newConnection();
		return connection;
	}
}
