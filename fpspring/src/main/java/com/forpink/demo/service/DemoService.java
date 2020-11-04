package com.forpink.demo.service;

import com.forpink.mvcframework.annotation.FPService;

@FPService
public class DemoService implements IDemoService {
	@Override
	public String get(String name) {
		return "My Name is " + name;
	}

}
