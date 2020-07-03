package com.mnuo.forpink.seckill.common.redis.distribute;

import org.redisson.Redisson;
import org.redisson.RedissonLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SentinelServersConfig;
import org.redisson.config.SingleServerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

@Configuration
@ConditionalOnClass(Config.class)
@EnableConfigurationProperties(RedissonProperties.class)
public class RedissonAutoConfiguration {
	@Autowired
	private RedissonProperties redissonProperties;
	/**
	 * 哨兵模式自动装配
	 * @return
	 */
//	@Bean
//	@ConditionalOnProperty(name="redisson.master-name")
	RedissonClient redissonSentinel(){
		Config config = new Config();
		SentinelServersConfig serversConfig = config.useSentinelServers()
				.addSentinelAddress(redissonProperties.getSentinelAddresses())
				.setMasterName(redissonProperties.getMasterName()).setTimeout(redissonProperties.getTimeout())
				.setMasterConnectionPoolSize(redissonProperties.getMasterConnectionPoolSize())
				.setSlaveConnectionPoolSize(redissonProperties.getSlaveConnectionPoolSize());
		
		if(!StringUtils.isEmpty(redissonProperties.getPassword())){
			serversConfig.setPassword(redissonProperties.getPassword());
		}
		return Redisson.create(config);
	}
	/**
	 * 单机模式自动装配
	 * @return
	 */
	@Bean
	@ConditionalOnProperty(name="redisson.address")
	RedissonClient redissonSingle(){
		Config config = new Config();
		SingleServerConfig serverConfig = config.useSingleServer()
				.setAddress(redissonProperties.getAddress())
				.setTimeout(redissonProperties.getTimeout())
				.setConnectionPoolSize(redissonProperties.getConnectionPoolSize())
				.setConnectionMinimumIdleSize(redissonProperties.getConnectionMinimumIdleSize());
		if(!StringUtils.isEmpty(redissonProperties.getPassword())){
			serverConfig.setPassword(redissonProperties.getPassword());
		}
		return Redisson.create(config);
	}
	/**
	 * 装配lock类, 并将实例注入到RedissonLockUtil中
	 * @param redissonClient
	 * @return
	 */
	@Bean
	RedissonLockUtil redissonLockUtil(RedissonClient redissonClient){
		RedissonLockUtil redissonLockUtil = new RedissonLockUtil();
		redissonLockUtil.setRedissonClient(redissonClient);
		return redissonLockUtil;
	}
	
}
