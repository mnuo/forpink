package com.mnuo.forpink.job;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Hello world!
 *
 */
@EnableScheduling
@SpringBootApplication
public class ForpinkJobApplication {
	public static void main(String[] args) {
		SpringApplication.run(ForpinkJobApplication.class, args);
	}
}
