package com.mnuo.forpink.core.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
@ConfigurationProperties(prefix = "service.client")
public class ClientEncodeConfig {
	private String clientId;
	private String clientSecret;
	private String[] grantType;
}
