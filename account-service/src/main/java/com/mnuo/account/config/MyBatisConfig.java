package com.mnuo.account.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan({"com.mnuo.account.mapper"})
public class MyBatisConfig {
}
