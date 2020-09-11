package com.mnuo.forpink.auth.service.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.query.NativeQuery;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.mnuo.forpink.core.common.RolePathVO;
import com.mnuo.forpink.core.module.RoleInfo;
import com.mnuo.forpink.core.respository.PermissionInfoRespository;
import com.mnuo.forpink.core.respository.RoleInfoRespository;
import com.mnuo.forpink.core.respository.UsersRespository;

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
	
	private Map<String, List<String>> resourceRolesMap;
	@Autowired
	RedisTemplate<String, Object> redisTemplate;
	@Autowired
	EntityManager entityManager;
	
	
	public boolean canAccess(HttpServletRequest request, Authentication authentication){
		String uri = request.getServletPath();
		log.info("uri: " + uri);
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
	@PostConstruct
    public void initData() {
		String sql = "select r.code role, p.path "
				+ " from role_info r, role_permission rp, permission_info p "
				+ " where r.id=rp.role_id and rp.permission_id=p.id ";
		Query query = entityManager.createNativeQuery(sql);
		query.unwrap(NativeQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
	    List<Map<String, String>> objs = query.getResultList();//这里的结果是Map,我们可以通过字段名称获取到相应的值
	    String irsStr = JSON.toJSONString(objs);
	    log.info(irsStr);
		List<RolePathVO> list = JSON.parseArray(irsStr, RolePathVO.class);
		
		if(!CollectionUtils.isEmpty(list)){
			Map<String, List<RolePathVO>> map = list.stream().collect(Collectors.groupingBy(RolePathVO::getPath));
			map.forEach((k,v)-> {
				resourceRolesMap.put(k, v.stream().map(RolePathVO::getRole).collect(Collectors.toList()));
			});
		}
		Map<String, List<String>> map = new HashMap<>();
		 redisTemplate.opsForHash().putAll("AUTH:RESOURCE_ROLES_MAP112", map);
         List<String> list222 =  (List<String>)redisTemplate.opsForHash().get("AUTH:RESOURCE_ROLES_MAP112", "	/api/api");
         log.info("aaa122-> " +  JSON.toJSONString(list222));
       
        redisTemplate.opsForHash().putAll("AUTH:RESOURCE_ROLES_MAP", resourceRolesMap);
        List<String> list1 =  (List<String>)redisTemplate.opsForHash().get("AUTH:RESOURCE_ROLES_MAP", "/api/sso/user/list");
        log.info("aaa-> " +  JSON.toJSONString(list1));
    }
	
}
