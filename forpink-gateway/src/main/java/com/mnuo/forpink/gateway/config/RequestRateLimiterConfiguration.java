package com.mnuo.forpink.gateway.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import reactor.core.publisher.Mono;

/**
 * IP限流
 * @author mon
 *
 */
@Configuration
public class RequestRateLimiterConfiguration {
	@Bean(value = "ipKeyResolver")
	public KeyResolver ipKeyResolver(){
		return exchange -> Mono.just(exchange.getRequest().getRemoteAddress().getAddress().getHostAddress());
	}
}
