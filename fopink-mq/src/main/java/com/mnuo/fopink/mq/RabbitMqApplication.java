package com.mnuo.fopink.mq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hello world!
 *
 *
 */
@SpringBootApplication
public class RabbitMqApplication {
	public static void main(String[] args) {
		SpringApplication.run(RabbitMqApplication.class, args);
		System.out.println("启动成功");
	}
}
