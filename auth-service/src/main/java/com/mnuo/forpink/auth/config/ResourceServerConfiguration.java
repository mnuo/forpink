package com.mnuo.forpink.auth.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

import com.mnuo.forpink.auth.handler.CustomAuthExceptionHandler;

@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter{
	public static final String ROLE_ADMIN = "ADMIN";
	@Autowired
	SecurityIgnoreConfig securityIgnoreConfig;
//	@Value("${service.ignore.resources}")
//	public List<String> ignoreResoures;// = {"/META-INF/resources", "/webjars/**","doc.html", "swagger-ui.html","/META-INF/resources/webjars/", "/oauth/*", "/auth/user/login"};
	
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
		List<String> resources = securityIgnoreConfig.getResources();
		String[] ignoreResoures = resources.toArray(new String[resources.size()]);
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
			.and()
			//请求权限配置
			.authorizeRequests()
			//下面的路径放行, 不需要经过认证
			.antMatchers(ignoreResoures).permitAll()
			.antMatchers("/swagger-ui.html").permitAll() // 任意访问
			.antMatchers("/doc.html").permitAll() // 任意访问
			.antMatchers("/swagger-resources/**").permitAll()
			.antMatchers("/webjars/**").permitAll()
			.antMatchers("/v2/**").permitAll()
			.antMatchers("/api/**").permitAll()
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
