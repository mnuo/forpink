package com.mnuo.forpink.seata.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
@EnableFeignClients
@EnableDiscoveryClient
@ComponentScan(basePackages={"com.mnuo.forpink.**"})
public class OrderApplication {
	public static void main(String[] args) {
		SpringApplication.run(OrderApplication.class, args);
		System.err.println("启动成功!");
	}
}
