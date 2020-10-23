package com.mnuo.forpink.seata.order.config;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.alibaba.druid.pool.DruidDataSource;

import io.seata.rm.datasource.DataSourceProxy;

@Configuration
public class SeataDataSourceAutoConfig {

	@Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource druidDataSource(){
        return new DruidDataSource();
    }
	@Primary
	@Bean("dataSource")
    public DataSourceProxy dataSourceProxy(DataSource dataSource) {
        return new DataSourceProxy(dataSource);
    }
//    @Bean
//    public EntityManagerFactory entityManager(DataSource dataSource) {
//        return new EntityManagerFactory();
//    }
}
