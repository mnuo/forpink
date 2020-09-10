package com.mnuo.forpink.gateway.auth.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.server.authentication.ServerFormLoginAuthenticationConverter;
import org.springframework.web.server.ServerWebExchange;

import com.alibaba.fastjson.JSON;

import reactor.core.publisher.Mono;

public class AuthenticationConverter extends ServerFormLoginAuthenticationConverter{
//	@Autowired
//	AuthorizationManager authorizationManager;
//    @Override
//    public Mono<Authentication> convert(ServerWebExchange exchange) {
//       //从session中获取登陆用户信息
//       String value = exchange.getSession().block().getAttribute("AccountInfo");
//       if(value == null) {
//           return Mono.empty();
//       } else {
//           return Mono.just(authorizationManager);
//       }
//    }
}