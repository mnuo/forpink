package com.forpink.framework.webmvc.context.support;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.forpink.framework.webmvc.beans.config.FPBeanDefinition;

public class FPDefaultListableBeanFactory extends FPAbstractApplicationContext{
	//存储注册信息的beanDefinition
	protected final Map<String, FPBeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

}
