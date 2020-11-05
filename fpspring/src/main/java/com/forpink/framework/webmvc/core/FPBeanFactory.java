package com.forpink.framework.webmvc.core;

/**
 * 单例工厂的顶层设计模式
 * @author administrator
 */
public interface FPBeanFactory {
	/**
	 * 根据beanName从Ioc容器中获取一个实例bean
	 * @param beanName
	 * @return
	 * @throws Exception
	 */
	Object getbean(String beanName) throws Exception;
	
	public Object getBean(Class<?> beanClass) throws Exception;
}
