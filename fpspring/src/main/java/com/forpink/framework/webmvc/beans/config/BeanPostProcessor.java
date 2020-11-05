package com.forpink.framework.webmvc.beans.config;
/**
 * 原生Spring中的beanPostProcessor是为对象初始化事件设置一种回调机制, 这个版本不做具体实现
 * @author administrator
 */
public class BeanPostProcessor {
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws Exception{
		return bean;
	}
	public Object postProcessAfterInitialization(Object bean, String beanName) throws Exception{
		return bean;
	}
}
