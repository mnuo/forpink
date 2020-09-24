package com.mnuo.forpink.designprinciples.model.decorator;

public class CarDecorator implements ICar{
	Car car;
	
	public CarDecorator(Car car){
		this.car = car;
	}
		
	@Override
	public void move() {
		car.move();
	}

}
