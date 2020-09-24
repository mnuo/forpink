package com.mnuo.forpink.designprinciples.model.factory.factorymethod;

public class Test {
	public static void main(String[] args) {
		Phone phone = new XiaomiFactory().getInstance();
		phone.whoami();
	}
}
