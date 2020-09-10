package com.mnuo.forpink.gateway.auth.config;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@ConfigurationProperties(prefix = "service.ignore")
@Data
public class SecurityIgnoreConfig {
    private List<String> resources;
}
