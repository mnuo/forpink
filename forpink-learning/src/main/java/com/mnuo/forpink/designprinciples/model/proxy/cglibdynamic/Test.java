package com.mnuo.forpink.designprinciples.model.proxy.cglibdynamic;

import org.springframework.cglib.proxy.Enhancer;

public class Test {
	public static void main(String[] args) {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(CgSubject.class);
		enhancer.setCallback(new HelloInterceptor());
		CgSubject subject = (CgSubject)enhancer.create();
		subject.sayhello();
	}
}
