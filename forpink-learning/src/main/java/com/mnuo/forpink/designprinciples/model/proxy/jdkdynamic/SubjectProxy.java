package com.mnuo.forpink.designprinciples.model.proxy.jdkdynamic;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class SubjectProxy implements InvocationHandler{
	Subject subject;
	public SubjectProxy(Subject subject) {
		this.subject = subject;
	}
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		System.out.println("before...");
		Object invoke = method.invoke(subject, args);
		System.out.println("afer..."+invoke);
		return invoke;
	}
	
}
