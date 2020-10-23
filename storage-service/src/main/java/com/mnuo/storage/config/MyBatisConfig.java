package com.mnuo.storage.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan({"com.mnuo.storage.mapper"})
public class MyBatisConfig {
}
