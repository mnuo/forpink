package com.mnuo.forpink.auth.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.oauth2.client.test.OAuth2ContextConfiguration.Password;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import com.mnuo.forpink.auth.exception.MssWebResponseExceptionTranslator;
import com.mnuo.forpink.auth.service.ForpinkUserDetailService;

/**
 * 〈OAuth2认证服务器〉
 *
 * @author Curise
 * @create 2018/12/13
 * @since 1.0.0
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;
//    @Autowired
//    private DataSource dataSource;

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Autowired
    private ForpinkUserDetailService userDetailService;
    

    @Bean
    public TokenStore tokenStore() {
        return new RedisTokenStore(redisConnectionFactory);
    }

//    @Primary
//    @Bean
//    public TokenStore jdbcTokenStore(){
//        return new JdbcTokenStore(dataSource);
//    }

    /**
     * 用来配置令牌端点(Token Endpoint)的安全约束
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
//        security
//                .allowFormAuthenticationForClients()
//                .tokenKeyAccess("permitAll()")
//                .checkTokenAccess("isAuthenticated()");
    	/**
    	 * 配置token获取和验证时的策略
    	 */
    	security.tokenKeyAccess("permitAll()")
    		.checkTokenAccess("isAuthenticated()")
    		.allowFormAuthenticationForClients();
    }
    
    /**
     * 配置客户端详情
     * 客户端详细信息在这里进行初始化, 你能够把客户端详情信息写死在这里或者是通过数据库来存储调取信息
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//        clients.withClientDetails(clientDetails());
    	
    	
//        clients.inMemory()
//                .withClient("android")
//                .scopes("read")
//                .secret("android")
//                .authorizedGrantTypes("password", "authorization_code", "refresh_token")
//                .and()
//                .withClient("webapp")
//                .scopes("read")
//                .authorizedGrantTypes("implicit")
//                .and()
//                .withClient("browser")
//                .authorizedGrantTypes("refresh_token", "password")
//                .scopes("read");
    	
    	clients.inMemory()
    		.withClient("client1")
    		.authorizedGrantTypes("authorization_code", "refresh_token") //授权方式: 授权码模式
    		.scopes("test")//授权范围
    		.secret(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode("123456"))
    		.and()
    		.withClient("client2")
    		.authorizedGrantTypes("password", "refresh_token")//授权方式: 密码模式
    		.scopes("test")//授权范围
    		.secret(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode("123456"));
    		
    	
    }
    /**
     * 用来配置授权（authorization）以及令牌（token）的访问端点和令牌服务(token services)
     * @param endpoints
     * @throws Exception
     */
  //使用最基本的InMemoryTokenStore生成token，即本地内存存储
   @Bean
   public TokenStore memoryTokenStore() {
     return new InMemoryTokenStore();
   }
//    @Bean
//    public ClientDetailsService clientDetails() {
//        return new JdbcClientDetailsService(dataSource);
//    }

    @Bean
    public WebResponseExceptionTranslator<?> webResponseExceptionTranslator(){
        return new MssWebResponseExceptionTranslator();
    }
    
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
//        endpoints.tokenStore(jdbcTokenStore())
//                .userDetailsService(userDetailService)
//                .authenticationManager(authenticationManager);
//        endpoints.tokenServices(defaultTokenServices());
        //认证异常翻译
       // endpoints.exceptionTranslator(webResponseExceptionTranslator());
    	
    	endpoints.authenticationManager(authenticationManager)
    		.tokenStore(memoryTokenStore())
    		.userDetailsService(userDetailsService);
    	
    }

    /**
     * <p>注意，自定义TokenServices的时候，需要设置@Primary，否则报错，</p>
     * @return
     */
//    @Primary
//    @Bean
//    public DefaultTokenServices defaultTokenServices(){
//        DefaultTokenServices tokenServices = new DefaultTokenServices();
//        tokenServices.setTokenStore(jdbcTokenStore());
//        tokenServices.setSupportRefreshToken(true);
//        // token有效期自定义设置，默认12小时
//        tokenServices.setAccessTokenValiditySeconds(60*60*12);
//        // refresh_token默认30天
//        tokenServices.setRefreshTokenValiditySeconds(60 * 60 * 24 * 7);
//        return tokenServices;
//    }
}
