package com.mnuo.forpink.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 *
 */
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan({"com.mnuo.forpink.**"})
public class ForpinkAuthApplication {
	public static void main(String[] args) {
		SpringApplication.run(ForpinkAuthApplication.class, args);
		System.err.println("启动成功");
	}
}
