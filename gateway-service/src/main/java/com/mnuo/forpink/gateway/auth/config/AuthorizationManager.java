package com.mnuo.forpink.gateway.auth.config;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import org.xmlunit.util.Convert;

import com.mnuo.forpink.gateway.auth.AuthConstant;
import com.mnuo.forpink.gateway.auth.RedisConstant;

import reactor.core.publisher.Mono;

/**
 * 鉴权管理器，用于判断是否有资源的访问权限
 * Created by macro on 2020/6/19.
 */
@Component
public class AuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> mono, AuthorizationContext authorizationContext) {
        //从Redis中获取当前路径可访问角色列表
        URI uri = authorizationContext.getExchange().getRequest().getURI();
//        Object obj = redisTemplate.opsForHash().get(RedisConstant.RESOURCE_ROLES_MAP, uri.getPath());
//        List<String> authorities = Convert.toList(String.class,obj);
        List<String> authorities =  (List<String>)redisTemplate.opsForHash().get("AUTH:RESOURCE_ROLES_MAP", "/api/sso/user/list");;
        authorities = authorities.stream().map(i -> i = AuthConstant.AUTHORITY_PREFIX + i).collect(Collectors.toList());
        //认证通过且角色匹配的用户可访问当前路径
        return mono
                .filter(Authentication::isAuthenticated)
                .flatMapIterable(Authentication::getAuthorities)
                .map(GrantedAuthority::getAuthority)
                .any(authorities::contains)
                .map(AuthorizationDecision::new)
                .defaultIfEmpty(new AuthorizationDecision(false));
//        
//        return mono
//                .filter(a -> a.isAuthenticated())
//                .flatMapIterable( a -> a.getAuthorities())
//                .map( g-> g.getAuthority())
//                .any(c->{
//                    //检测权限是否匹配
//                    String[] roles = c.split(",");
//                    for(String role:roles) {
//                        if(authorities.contains(role)) {
//                            return true;
//                        }
//                    }
//                    return false;
//                })
//                .map( hasAuthority -> new AuthorizationDecision(hasAuthority))
//                .defaultIfEmpty(new AuthorizationDecision(false));
        
    }

}
