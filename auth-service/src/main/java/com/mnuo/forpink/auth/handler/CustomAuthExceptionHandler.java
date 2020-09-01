package com.mnuo.forpink.auth.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.mnuo.forpink.auth.common.ResponseType;
import com.mnuo.forpink.auth.vo.Response;

import lombok.extern.slf4j.Slf4j;
/**
 * 自定义未授权, token无效 权限不足返回信息处理类
 * @author administrator
 */
@Slf4j
@Component
public class CustomAuthExceptionHandler implements AuthenticationEntryPoint, AccessDeniedHandler{

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		response.setContentType("application/json;charset=UTF-8");
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Cache-Control","no-cache");
		response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
		response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
		response.addHeader("Access-Control-Max-Age", "1800");
		//访问资源的用户权限不足
		log.error("AccessDeniedException : {}",accessDeniedException.getMessage());
		response.getWriter().write(JSON.toJSONString(Response.error(ResponseType.INSUFFICIENT_PERMISSIONS)));
	}

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		Throwable cause = authException.getCause();
		response.setContentType("application/json;charset=UTF-8");
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		//CORS "pre-flight" request
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Cache-Control","no-cache");
		response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
		response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
		response.addHeader("Access-Control-Max-Age", "1800");
		
		if(cause instanceof InvalidTokenException){
			log.error("InvalidTokenException: {}", cause.getMessage());
			//token无效
			response.getWriter().write(JSON.toJSONString(Response.error(ResponseType.ACCESS_TOKEN_INVALID)));
		}else{
			log.error("AuthenticationException: NoAuthentication");
			//资源未授权
			response.getWriter().write(JSON.toJSONString(Response.error(ResponseType.UNAUTHORIZED)));
		}
	}

}
