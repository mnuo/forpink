package com.mnuo.forpink.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.mnuo.forpink.core.respository.RoleInfoRespository;
import com.mnuo.forpink.core.respository.UsersRespository;

import lombok.extern.slf4j.Slf4j;

/**
 * Security核心配置, 承担的角色是用户资源管理, 在客户端发送登录请求的时候, security会将先去根据用户输入的用户名和密码, 去查询数据库
 * 	如果匹配, 将用户信息进行一层转换, 然后交给认证授权管理器, 认证授权管理器根据用户信息分发一个token给用户, 下次请求的时候携带这个token
 * 认证管理器就可以根据这个token找到用户信息
 * @author administrator
 */
@Configuration
@Slf4j
@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	UsersRespository usersRespository;
	@Autowired
	RoleInfoRespository roleInfoRespository;
	@Autowired
	UserDetailsService userDetailsService;
	
//	@Autowired
//	private RestTemplate restTemplate;
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
//	@Override
//	protected UserDetailsService userDetailsService() {
//		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
//		return new UserDetailsService() {
//			
//			@Override
//			public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//				log.info("username: {}", username);
//				Users user = usersRespository.findByUserName(username);
//				if(user != null){
//					CustomUserDetail detail = new CustomUserDetail();
//					detail.setUsername(username);
//					detail.setPassword("{bcrypt}"+bCryptPasswordEncoder.encode(user.getPassWord()));
//					
//					List<RoleInfo> roles = roleInfoRespository.findRolesByUser(user.getId());
//					if(!CollectionUtils.isEmpty(roles)){
//						List<String> roleNames = roles.stream().map(RoleInfo::getCode).collect(Collectors.toList());
//						String[] role = (String[]) roleNames.toArray();
//						List<GrantedAuthority> list = AuthorityUtils.createAuthorityList(role);
//						detail.setAuthorities(list);
//					}
//					return detail;
//				}
//				return null;
//			}
//		};
//	}
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService)
			.passwordEncoder(passwordEncoder());
	}
	@Bean
	PasswordEncoder passwordEncoder(){
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

}
