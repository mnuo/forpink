package com.mnuo.forpink.designprinciples.model.factory.factorymethod;

public class IphoneFactory implements PhoneFactory {

	@Override
	public Phone getInstance() {
		return new Iphone();
	}

}
