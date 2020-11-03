package com.forpink.demo.service;

import com.forpink.mvcframework.annotation.FPService;

@FPService
public class DemoServiceImpl implements IDemoService {
	@Override
	public String get(String name) {
		return "My Name is " + name;
	}

}
