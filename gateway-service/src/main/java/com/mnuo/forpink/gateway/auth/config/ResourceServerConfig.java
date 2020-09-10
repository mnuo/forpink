package com.mnuo.forpink.gateway.auth.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import com.mnuo.forpink.gateway.auth.component.RestAuthenticationEntryPoint;
import com.mnuo.forpink.gateway.auth.component.RestfulAccessDeniedHandler;
import com.mnuo.forpink.gateway.auth.filter.IgnoreUrlsRemoveJwtFilter;

import lombok.AllArgsConstructor;

/**
 * 资源服务器配置
 * Created by macro on 2020/6/19.
 */
@AllArgsConstructor
@Configuration
@EnableWebFluxSecurity
public class ResourceServerConfig {
    private final AuthorizationManager authorizationManager;
    private final SecurityIgnoreConfig securityIgnoreConfig;
    private final RestfulAccessDeniedHandler restfulAccessDeniedHandler;
    private final RestAuthenticationEntryPoint restAuthenticationEntryPoint;
    private final IgnoreUrlsRemoveJwtFilter ignoreUrlsRemoveJwtFilter;
//
//    @Bean
//    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
//        http.oauth2ResourceServer().jwt()
//                .jwtAuthenticationConverter(jwtAuthenticationConverter());
//        //自定义处理JWT请求头过期或签名错误的结果
//        http.oauth2ResourceServer().authenticationEntryPoint(restAuthenticationEntryPoint);
//        //对白名单路径，直接移除JWT请求头
//        http.addFilterBefore(ignoreUrlsRemoveJwtFilter, SecurityWebFiltersOrder.AUTHENTICATION);
//        http.authorizeExchange()
//                .pathMatchers(ignoreUrlsConfig.getResources().toArray(new String[ignoreUrlsConfig.getResources().size()])).permitAll()//白名单配置
//                .anyExchange().access(authorizationManager)//鉴权管理器配置
//                .and().exceptionHandling()
//                .accessDeniedHandler(restfulAccessDeniedHandler)//处理未授权
//                .authenticationEntryPoint(restAuthenticationEntryPoint)//处理未认证
//                .and().csrf().disable();
//        return http.build();
//    }
//
//    @Bean
//    public Converter<Jwt, ? extends Mono<? extends AbstractAuthenticationToken>> jwtAuthenticationConverter() {
//        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
//        jwtGrantedAuthoritiesConverter.setAuthorityPrefix(AuthConstant.AUTHORITY_PREFIX);
//        jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName(AuthConstant.AUTHORITY_CLAIM_NAME);
//        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
//        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
//        return new ReactiveJwtAuthenticationConverterAdapter(jwtAuthenticationConverter);
//    }
    @Bean
    public SecurityWebFilterChain springSecurityWebFilterChain(ServerHttpSecurity http){
    	List<String> resources = securityIgnoreConfig.getResources();
		String[] ignoreResoures = resources.toArray(new String[resources.size()]);
//		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
//			.and()
			//请求权限配置
			http.authorizeExchange()
			//下面的路径放行, 不需要经过认证
			.pathMatchers(ignoreResoures).permitAll()
			.pathMatchers("/swagger-ui.html").permitAll() // 任意访问
			.pathMatchers("/doc.html").permitAll() // 任意访问
			.pathMatchers("/swagger-resources/**").permitAll()
			.pathMatchers("/webjars/**").permitAll()
			.pathMatchers("/api/**/v2/api-docs").permitAll()
//			.pathMatchers("/api/**").permitAll()
			//Option请求不需要鉴权
			.pathMatchers(HttpMethod.OPTIONS, "/**").permitAll()
			.anyExchange().access(authorizationManager)//鉴权管理器配置
			.and().exceptionHandling()
			.accessDeniedHandler(restfulAccessDeniedHandler)//处理未授权
			.authenticationEntryPoint(restAuthenticationEntryPoint)//处理未认证
			.and().csrf().disable();
			//获取角色 权限列表接口只允许系统管理员访问
			// 其余接口没有角色权限, 但需要经过认证, 只要携带token就可以放行
//			.anyRequest()
//			.authenticated();
//			.anyRequest()
//			.access("@authService.canAccess(request,authentication)");
			return http.build();
    }
}
