package com.mnuo.forpink.designprinciples.model.proxy.staticproxy;

public class Test {
	public static void main(String[] args) {
		SomeServiceProxy proxy = new SomeServiceProxy(new SomeServiceImpl());
		proxy.raining();
	}
}
