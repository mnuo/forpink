package com.mnuo.forpink.designprinciples.model.factory.factorymethod;

public class XiaomiFactory implements PhoneFactory{

	@Override
	public Phone getInstance() {
		return new Xiaomi();
	}

}
