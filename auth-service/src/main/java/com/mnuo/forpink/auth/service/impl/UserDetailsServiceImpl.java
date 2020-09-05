package com.mnuo.forpink.auth.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.mnuo.forpink.auth.domain.CustomUserDetail;
import com.mnuo.forpink.core.module.RoleInfo;
import com.mnuo.forpink.core.module.Users;
import com.mnuo.forpink.core.respository.RoleInfoRespository;
import com.mnuo.forpink.core.respository.UsersRespository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService{
	@Autowired
	UsersRespository usersRespository;
	@Autowired
	RoleInfoRespository roleInfoRespository;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		log.info("username: {}", username);
		Users user = usersRespository.findByUserName(username);
		if(user != null){
			CustomUserDetail detail = new CustomUserDetail();
			detail.setUsername(username);
			detail.setPassword("{bcrypt}"+bCryptPasswordEncoder.encode(user.getPassWord()));
			
			List<RoleInfo> roles = roleInfoRespository.findRolesByUser(user.getId());
			if(!CollectionUtils.isEmpty(roles)){
				List<String> roleNames = roles.stream().map(RoleInfo::getCode).collect(Collectors.toList());
				String[] role = roleNames.toArray(new String[roleNames.size()]);
				List<GrantedAuthority> list = AuthorityUtils.createAuthorityList(role);
				detail.setAuthorities(list);
			}
			return detail;
		}
		return null;
	}

}
