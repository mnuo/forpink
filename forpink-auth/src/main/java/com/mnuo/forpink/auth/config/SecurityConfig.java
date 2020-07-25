package com.mnuo.forpink.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import com.mnuo.forpink.auth.service.ForpinkUserDetailService;

/**
 * 〈security配置〉
 *  配置授权码/账号密码是验证输入的束缚正确
 *
 * @author
 * @create 2018/12/13
 * @since 1.0.0
 */
@Configuration
@EnableWebSecurity //开取权限验证
@Order(2)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private ForpinkUserDetailService userDetailService;
    
    /**
     * 配置用户, 使用内存的用户, 实际项目中,一般使用的是数据库保存用户, 具体的实现类可以用jdbcdaoimpl或者jdbcUserDetailsManager
     */
    @Override
    @Bean
    protected UserDetailsService userDetailsService(){
    	InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
    	manager.createUser(User.withUsername("admin").password(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode("admin")).authorities("USER").build());
    	return manager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new ForpinkBCryptPasswordEncoder();
//        return new NoEncryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.requestMatchers().antMatchers("/oauth/**")
                .and()
                .authorizeRequests()
                .antMatchers("/oauth/**").authenticated()
                .and()
                .csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailService).passwordEncoder(passwordEncoder());
    	auth.userDetailsService(userDetailsService());
    }

    /**
     * 不定义没有password grant_type,密码模式需要AuthenticationManager支持
     * 配置这个bean会在做AuthorizationServerConfigurer配置的时候使用
     * @return
     * @throws Exception
     */
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
