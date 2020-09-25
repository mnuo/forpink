package com.mnuo.forpink.designprinciples.model.strategy;

public class VIPCustomer implements Customer{

	@Override
	public double getDiscoutPrice(double price) {
		return price*0.75;
	}

}
