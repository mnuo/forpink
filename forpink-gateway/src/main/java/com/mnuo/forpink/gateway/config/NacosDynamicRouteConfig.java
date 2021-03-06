package com.mnuo.forpink.gateway.config;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
//@Component
@Service
public class NacosDynamicRouteConfig implements ApplicationEventPublisherAware{
	@Value("${nacos.config.data-id}")
	private String dataId;
	@Value("${nacos.config.group}")
	private String group;
	
	@Value("${spring.cloud.nacos.config.server-addr}")
	private String serverAddr;
	
	@Autowired
	private RouteDefinitionWriter routeDefinitionWriter;
	
	private ApplicationEventPublisher applicationEventPublisher;
	
	private static final List<String> ROUTE_LIST = new ArrayList<>();
	
	@PostConstruct
	public void dynamicRouteByNacosListener(){
		try {
			ConfigService configService = NacosFactory.createConfigService(serverAddr);
			String config = configService.getConfig(dataId, group, 5000);
			publisher(config);
			configService.addListener(dataId, group, new Listener() {
				
				@Override
				public void receiveConfigInfo(String configInfo) {
					publisher(configInfo);
				}
				
				@Override
				public Executor getExecutor() {
					// TODO Auto-generated method stub
					return null;
				}
			});
		} catch (Exception e) {
			log.info("监听数据异常: " + e.getMessage(), e);
		}
	}
	/**
	 * 发布路由
	 */
	private void publisher(String config) {
	    clearRoute();
	    try {
	        log.info("重新更新动态路由");
	        List<RouteDefinition> gateway = JSONObject.parseArray(config, RouteDefinition.class);
	        for(RouteDefinition route: gateway) {
	            addRoute(route);
	        }
	        applicationEventPublisher.publishEvent(new RefreshRoutesEvent(this.routeDefinitionWriter));
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    
	}
	private void clearRoute(){
		for (String id : ROUTE_LIST) {
			this.routeDefinitionWriter.delete(Mono.just(id)).subscribe();
		}
		ROUTE_LIST.clear();
	}
	private void addRoute(RouteDefinition definition){
		try {
			routeDefinitionWriter.save(Mono.just(definition)).subscribe();
			ROUTE_LIST.add(definition.getId());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	private void publish(){
		this.applicationEventPublisher.publishEvent(new RefreshRoutesEvent(this.routeDefinitionWriter));
	}
	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher){
		this.applicationEventPublisher = applicationEventPublisher;
				
	}
}
