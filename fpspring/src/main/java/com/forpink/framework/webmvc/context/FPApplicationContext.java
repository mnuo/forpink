package com.forpink.framework.webmvc.context;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.forpink.framework.webmvc.beans.FPBeanWrapper;
import com.forpink.framework.webmvc.beans.config.BeanPostProcessor;
import com.forpink.framework.webmvc.beans.config.FPBeanDefinition;
import com.forpink.framework.webmvc.context.support.FPBeanDefinitionReader;
import com.forpink.framework.webmvc.context.support.FPDefaultListableBeanFactory;
import com.forpink.framework.webmvc.core.FPAutowired;
import com.forpink.framework.webmvc.core.FPBeanFactory;
import com.forpink.framework.webmvc.core.FPController;
import com.forpink.framework.webmvc.core.FPService;
/**
 * 按照源码分析的套路, IOC,DI,MVC,AOP
 * @author administrator
 */
public class FPApplicationContext extends FPDefaultListableBeanFactory implements FPBeanFactory{
	
	private String[] configLocations;
	private FPBeanDefinitionReader reader;
	
	//用来保证注册式单例模式
	private Map<String, Object> factoryBeanObjectCache = new HashMap<>();
	
	private Map<String, FPBeanWrapper> factoryBeanInstanceCache = new ConcurrentHashMap<>(); 
	@Override
	public Object getbean(String beanName) throws Exception {
		FPBeanDefinition beanDefinition = super.beanDefinitionMap.get(beanName);
		try {
			//生成通知事件
			BeanPostProcessor beanPostProcessor = new BeanPostProcessor();
			Object instance = instantiateBean(beanDefinition);
			if( null == instance){ return null;}
			//在实例化调用之前调用
			beanPostProcessor.postProcessBeforeInitialization(instance, beanName);
			FPBeanWrapper wrapper = new FPBeanWrapper(instance);
			this.factoryBeanInstanceCache.put(beanName, wrapper);
			//实例化后调用
			beanPostProcessor.postProcessAfterInitialization(instance, beanName);
			populationBean(beanName, instance);
			
			//通过这样子调用, 相当于给我们自己留有了可操作空间
			return this.factoryBeanInstanceCache.get(beanName).getWrappedInstance();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private void populationBean(String beanName, Object instance) {
		Class<? extends Object> clazz = instance.getClass();
		if(!(clazz.isAnnotationPresent(FPController.class)) || !(clazz.isAnnotationPresent(FPService.class))){
			return;
		}
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			if(!field.isAnnotationPresent(FPAutowired.class)){continue;}
			
			FPAutowired autowired = field.getAnnotation(FPAutowired.class);
			String autowiredBeanName = autowired.value().trim();
			if("".equals(autowiredBeanName)){
				autowiredBeanName = field.getType().getName();
			}
			field.setAccessible(true);
			try {
				field.set(instance, this.factoryBeanInstanceCache.get(autowiredBeanName).getWrappedInstance());
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		
	}

	private Object instantiateBean(FPBeanDefinition beanDefinition) {
		Object instance = null;
		String className = beanDefinition.getBeanClassName();
		try {
			if(this.factoryBeanObjectCache.containsKey(className)){
				instance = this.factoryBeanObjectCache.get(className);
			}else{
				Class<?> clazz = Class.forName(className);
				instance = clazz.newInstance();
				this.factoryBeanObjectCache.put(beanDefinition.getFactoryBeanName(), instance);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Object getBean(Class<?> beanClass) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
