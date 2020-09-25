package com.mnuo.forpink.designprinciples.model.proxy.jdkdynamic;

import java.lang.reflect.Proxy;

public class Test {
	public static void main(String[] args) {
		Subject subject = new SubjectImpl();
		SubjectProxy proxy = new SubjectProxy(subject);
		Subject sub = (Subject)Proxy.newProxyInstance(subject.getClass().getClassLoader(), subject.getClass().getInterfaces(), proxy);
		sub.hello();
	}
}
