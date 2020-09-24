package com.mnuo.forpink.designprinciples.model.decorator;

public class FlyCar extends CarDecorator {

	public FlyCar(Car car) {
		super(car);
	}
	public void flyCar(){
		System.out.println("they said that i can fly");
	}
	
	@Override
	public void move() {
		super.move();
		flyCar();
	}
}
