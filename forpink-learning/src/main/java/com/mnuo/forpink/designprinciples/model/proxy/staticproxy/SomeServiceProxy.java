package com.mnuo.forpink.designprinciples.model.proxy.staticproxy;

public class SomeServiceProxy implements ISomeService{
	ISomeService someService;
	SomeServiceProxy(ISomeService someService){
		this.someService = someService;
	}
	@Override
	public void raining() {
		someService.raining();
	}

}
