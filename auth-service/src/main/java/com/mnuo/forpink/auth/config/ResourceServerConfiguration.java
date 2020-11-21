package com.mnuo.forpink.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.expression.OAuth2WebSecurityExpressionHandler;

import com.mnuo.forpink.auth.handler.CustomAuthExceptionHandler;

/**
 * 资源服务器
 * @author administrator
 */
//@Configuration
//@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter{
	public static final String ROLE_ADMIN = "ADMIN";
	
	@Autowired
	private CustomAuthExceptionHandler customAuthExceptionHandler;
	
//	@Autowired
//	OAuth2WebSecurityExpressionHandler oauth2WebSecurityExpressionHandler;
	
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.stateless(false)
//			.expressionHandler(oauth2WebSecurityExpressionHandler)
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
//			.antMatchers(ignoreResoures).permitAll()
			.antMatchers("/swagger-ui.html").permitAll() // 任意访问
			.antMatchers("/doc.html").permitAll() // 任意访问
			.antMatchers("/swagger-resources/**").permitAll()
			.antMatchers("/webjars/**").permitAll()
			.antMatchers("/v2/**").permitAll()
			.antMatchers("/api/**").permitAll()
			//Option请求不需要鉴权
			.antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
			//用户的增删改查接口只允许管理员访问
			//获取角色 权限列表接口只允许系统管理员访问
			.antMatchers(HttpMethod.GET, "/auth/role").hasAnyAuthority(ROLE_ADMIN)
			.antMatchers("/oauth/**").permitAll()
			// 其余接口没有角色权限, 但需要经过认证, 只要携带token就可以放行
			.anyRequest()
			.authenticated();
//			.anyRequest()
//			.access("@authService.canAccess(request,authentication)");
	}
	
	/**
	 * 解决办法··························
	 * Failed to evaluate expression '#oauth2.throwOnError
	 * No bean resolver registered in the context to resolve access to bean
	 * @param applicationContext
	 * @return
	 */
//	@Bean
//	@Primary
	public OAuth2WebSecurityExpressionHandler oAuth2WebSecurityExpressionHandler(ApplicationContext applicationContext) {
	    OAuth2WebSecurityExpressionHandler expressionHandler = new OAuth2WebSecurityExpressionHandler();
	    expressionHandler.setApplicationContext(applicationContext);
	    return expressionHandler;
	}
	
}
