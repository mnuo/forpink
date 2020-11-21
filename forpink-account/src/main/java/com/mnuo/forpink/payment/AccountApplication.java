package com.mnuo.forpink.payment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hello world!
 *
 */
@SpringBootApplication
public class AccountApplication {
	public static void main(String[] args) {
		SpringApplication.run(AccountApplication.class, args);
		System.err.println("server start finish");
	}
}
