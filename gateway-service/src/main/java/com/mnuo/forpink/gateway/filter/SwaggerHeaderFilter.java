package com.mnuo.forpink.gateway.filter;

import java.net.URI;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;

import com.mnuo.forpink.gateway.swagger.ForpinkSwaggerResourceProvider;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class SwaggerHeaderFilter extends AbstractGatewayFilterFactory {
	private static final String HEADER_NAME = "X-Forwarded-Prefix-MY";

	@Override
	public GatewayFilter apply(Object config) {
		return (exchange, chain) -> {
			ServerHttpRequest request = exchange.getRequest();
			String path = request.getURI().getPath();
			if (!StringUtils.endsWithIgnoreCase(path, ForpinkSwaggerResourceProvider.API_URI)) {
				return chain.filter(exchange);
			}
			
//			 ServerHttpRequest req = exchange.getRequest();
//		      ServerWebExchangeUtils.addOriginalRequestUrl(exchange, req.getURI());
//		      String path1 = req.getURI().getRawPath();
//		      String newPath = path1.replaceAll("(?<,/api>^)", "/api$\\{oldPath}");
//		      ServerHttpRequest request1 = req.mutate().path(newPath).build();
//		      exchange.getAttributes().put(ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR, request1.getURI());
//		      return chain.filter(exchange.mutate().request(request1).build());
		      
			String basePath = path.substring(0, path.lastIndexOf(ForpinkSwaggerResourceProvider.API_URI));
			ServerHttpRequest newRequest = request.mutate().header(HEADER_NAME, new String[]{basePath}).build();
			ServerWebExchange newExchange = exchange.mutate().request(newRequest).build();
			return chain.filter(newExchange);
		};
	}
}
