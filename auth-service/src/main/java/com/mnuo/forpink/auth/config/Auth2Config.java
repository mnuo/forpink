package com.mnuo.forpink.auth.config;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import com.mnuo.forpink.auth.handler.CustomAuthExceptionHandler;

import lombok.extern.slf4j.Slf4j;

/**
 * oauth2服务器配置
 * @author administrator
 */
//@Configuration
//@Slf4j
public class Auth2Config {
	public static final String ROLE_ADMIN = "ADMIN";
	//访问客户端秘钥
	public static final String CLIENT_SECRET = "123456";
	
	public static final String CLIENT_ID = "client_1";
	public static final String[] IGNORE_RESOURES = {"/META-INF/resources", "/webjars/**","doc.html", "swagger-ui.html","/META-INF/resources/webjars/", "/oauth/*", "/auth/user/login"};
	
	//鉴权模式
	public static final String GRANT_TYPE[] = {"password", "refresh_token"}; 
	
	/**
	 * 资源服务器
	 * @author administrator
	 */
//	@Configuration
//	@EnableResourceServer
	protected static class ResourceServerConfiguration extends ResourceServerConfigurerAdapter{
		@Autowired
		private CustomAuthExceptionHandler customAuthExceptionHandler;
		
		@Override
		public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
			resources.stateless(false)
				.accessDeniedHandler(customAuthExceptionHandler)
				.authenticationEntryPoint(customAuthExceptionHandler);
		}
		@Override
		public void configure(HttpSecurity http) throws Exception {
			http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
				.and()
				//请求权限配置
				.authorizeRequests()
				//下面的路径放行, 不需要经过认证
				.antMatchers("/META-INF/resources", "/webjars/**","doc.html", "swagger-ui.html","/META-INF/resources/webjars/", "/oauth/*", "/auth/user/login").permitAll()
				//Option请求不需要鉴权
				.antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
				//用户的增删改查接口只允许管理员访问
				.antMatchers(HttpMethod.POST, "/auth/user").hasAuthority(ROLE_ADMIN)
				.antMatchers(HttpMethod.PUT, "/auth/user").hasAuthority(ROLE_ADMIN)
				.antMatchers(HttpMethod.DELETE, "/auth/user").hasAuthority(ROLE_ADMIN)
				//获取角色 权限列表接口只允许系统管理员访问
				.antMatchers(HttpMethod.GET, "/auth/role").hasAnyAuthority(ROLE_ADMIN)
				// 其余接口没有角色权限, 但需要经过认证, 只要携带token就可以放行
				.anyRequest()
				.authenticated();
		}
	}
	
	/**
	 * 权限认证授权服务器
	 * @author administrator
	 */
//	@Configuration
//	@EnableAuthorizationServer
	protected static class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter{
		@Autowired
		private AuthenticationManager authenticationManager;
		
		@Autowired
		private RedisConnectionFactory connectionFactory;
		
		@Override
		public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
			String finalSecret = "{bcrypt}" + new BCryptPasswordEncoder().encode(CLIENT_SECRET);
			//配置客户端, 使用密码模式验证鉴权
			clients.inMemory()
				.withClient(CLIENT_ID)
				//密码模式和刷新token模式
				.authorizedGrantTypes(GRANT_TYPE)
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
//			endpoints.tokenStore(new InMemoryTokenStore())
//				.authenticationManager(authenticationManager);
			
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
		}
		@Override
		public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
			//允许表单认证
			security.allowFormAuthenticationForClients()
				.tokenKeyAccess("isAuthenticated()")
				.checkTokenAccess("permitAll()");
		}
		
	}
}
