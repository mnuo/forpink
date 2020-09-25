package com.mnuo.forpink.designprinciples.model.proxy.cglibdynamic;

import java.lang.reflect.Method;

import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

public class HelloInterceptor implements MethodInterceptor{

	@Override
	public Object intercept(Object o, Method method, Object[] arg2, MethodProxy proxy) throws Throwable {
		System.out.println("before...");
		Object obj = proxy.invokeSuper(o, arg2);
		System.out.println("after...");
		return obj;
	}

}
