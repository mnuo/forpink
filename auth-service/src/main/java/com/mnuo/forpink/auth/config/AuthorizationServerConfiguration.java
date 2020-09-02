package com.mnuo.forpink.auth.config;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
/**
 * 权限认证授权服务器
 * @author administrator
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter{
	//访问客户端秘钥
//	public static final String CLIENT_SECRET = "123456";
//	public static final String CLIENT_ID = "client_1";
	@Autowired
	ClientEncodeConfig clientEncodeConfig;
	//鉴权模式
	public static final String GRANT_TYPE[] = {"password", "refresh_token"}; 
		

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private RedisConnectionFactory connectionFactory;
	
	@Autowired
	UserDetailsService userDetailsService;
	
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		String finalSecret = "{bcrypt}" + new BCryptPasswordEncoder().encode(clientEncodeConfig.getClientSecret());
		//配置客户端, 使用密码模式验证鉴权
		clients.inMemory()
			.withClient(clientEncodeConfig.getClientId())
			//密码模式和刷新token模式
			.authorizedGrantTypes(clientEncodeConfig.getGrantType())
			.scopes("all")
			.secret(finalSecret);

	}
	@Bean
	public RedisTokenStore redisTokenStore(){
		return new RedisTokenStore(connectionFactory);
	}
	/**
	 * token及用户信息存储到redis, 当然你也可以存储到当前服务器的服务内存, 但不推荐
	 */
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		//token信息保存到服务内存
//		endpoints.tokenStore(new InMemoryTokenStore())
//			.authenticationManager(authenticationManager);
		
		//token信息保存到redis
		endpoints.tokenStore(redisTokenStore()).authenticationManager(authenticationManager);
		//配置TokenService的参数
		DefaultTokenServices tokenServices = new DefaultTokenServices();
		tokenServices.setTokenStore(endpoints.getTokenStore());
		tokenServices.setSupportRefreshToken(true);
		tokenServices.setClientDetailsService(endpoints.getClientDetailsService());
		tokenServices.setTokenEnhancer(endpoints.getTokenEnhancer());
		//1小时
		tokenServices.setAccessTokenValiditySeconds((int) TimeUnit.HOURS.toSeconds(1));
		//1小时
		tokenServices.setRefreshTokenValiditySeconds((int) TimeUnit.HOURS.toSeconds(1));
		tokenServices.setReuseRefreshToken(false);
		endpoints.tokenServices(tokenServices);
		endpoints.userDetailsService(userDetailsService);
	}
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		//允许表单认证
		security.allowFormAuthenticationForClients()
			.tokenKeyAccess("isAuthenticated()")
			.checkTokenAccess("permitAll()");
	}
	
}
