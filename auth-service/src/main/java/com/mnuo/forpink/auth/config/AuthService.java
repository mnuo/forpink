package com.mnuo.forpink.auth.config;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.mnuo.forpink.auth.module.RoleInfo;
import com.mnuo.forpink.auth.respository.PermissionInfoRespository;
import com.mnuo.forpink.auth.respository.RoleInfoRespository;
import com.mnuo.forpink.auth.respository.UsersRespository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class AuthService {
	@Autowired
	UsersRespository usersRespository;
	@Autowired
	RoleInfoRespository roleInfoRespository;
	@Autowired
	PermissionInfoRespository permissionInfoRespository;
	
	@Value("${server.servlet.context-path}")
	String serverName;
	
	public boolean canAccess(HttpServletRequest request, Authentication authentication){
		String uri = request.getRequestURI();
		log.info("uri: " + uri);
		if(!StringUtils.isEmpty(serverName)){
			uri = uri.replace("/" + serverName, "");
		}
		List<RoleInfo> roles = getRolesForResources(uri);
		if(!CollectionUtils.isEmpty(roles)){
			for (RoleInfo role : roles) {
                for (GrantedAuthority grantedAuthority : authentication.getAuthorities()) {
                    if (role.getCode().equals(grantedAuthority.getAuthority())) {
                        return true;
                    }
                }
            }
		}
		return false;
	}

	private List<RoleInfo> getRolesForResources(String uri) {
		if(StringUtils.isEmpty(uri)){
			return Collections.emptyList();
		}
		List<RoleInfo> roles = roleInfoRespository.findRolesByPermission(uri);
		return roles;
	}

	
}
