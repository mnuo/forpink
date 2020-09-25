package com.mnuo.forpink.designprinciples.model.strategy;

public class DefaultCustomer implements Customer{

	@Override
	public double getDiscoutPrice(double price) {
		return price*1.5;
	}

}
