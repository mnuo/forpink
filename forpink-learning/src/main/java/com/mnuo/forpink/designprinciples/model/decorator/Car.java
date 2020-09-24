package com.mnuo.forpink.designprinciples.model.decorator;

public class Car implements ICar {

	@Override
	public void move() {
		System.out.println("the car can move....");
	}

}
