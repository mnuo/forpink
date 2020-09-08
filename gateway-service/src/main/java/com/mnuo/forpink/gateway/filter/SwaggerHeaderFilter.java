package com.mnuo.forpink.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;

import com.mnuo.forpink.gateway.swagger.ForpinkSwaggerResourceProvider;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class SwaggerHeaderFilter implements GlobalFilter, Ordered {

	@Override
	public int getOrder() {
		return 0;
	}

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
          ServerHttpRequest request = exchange.getRequest();
          String path = request.getURI().getPath();
          if (!StringUtils.endsWithIgnoreCase(path, ForpinkSwaggerResourceProvider.SWAGGER2URL)) {
              return chain.filter(exchange);
          }
          String basePath = path.substring(0, path.lastIndexOf(ForpinkSwaggerResourceProvider.SWAGGER2URL));
          log.info("basepath: " + basePath);

          ServerHttpRequest newRequest = request.mutate().path(basePath).build();// .header("X-Forwarded-Prefix", basePath).build();
          ServerWebExchange newExchange = exchange.mutate().request(newRequest).build();
          return chain.filter(newExchange);
          
//          ServerHttpRequest newRequest = request.mutate().build();
//          ServerWebExchange newExchange = exchange.mutate().request(newRequest).build();
//          return chain.filter(newExchange);
	}
//	public class SwaggerHeaderFilter extends AbstractGatewayFilterFactory<Object> {
//    @Override
//    public GatewayFilter apply(Object config) {
//        return (exchange, chain) -> {
//            ServerHttpRequest request = exchange.getRequest();
//            String path = request.getURI().getPath();
//            if (!StringUtils.endsWithIgnoreCase(path, ForpinkSwaggerResourceProvider.SWAGGER2URL)) {
//                return chain.filter(exchange);
//            }
//            ServerHttpRequest newRequest = request.mutate().build();
//            ServerWebExchange newExchange = exchange.mutate().request(newRequest).build();
//            return chain.filter(newExchange);
//        };
//    }
}
