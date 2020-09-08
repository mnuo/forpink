package com.mnuo.forpink.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 *
 */
@EnableDiscoveryClient
@SpringBootApplication
public class GatewayAppliation {
	public static void main(String[] args) {
		SpringApplication.run(GatewayAppliation.class, args);
		System.err.println("启动成功");
	}
}
