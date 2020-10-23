package com.mnuo.order.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan({"com.mnuo.order.mapper"})
public class MyBatisConfig {
}
