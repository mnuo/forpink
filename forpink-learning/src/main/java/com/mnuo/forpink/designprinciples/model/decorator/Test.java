package com.mnuo.forpink.designprinciples.model.decorator;
/**
 * decorator和桥接, 都是为了解决多子类问题, 但是桥接现有功能不稳定, decorator是增加新功能
 */
public class Test {
	public static void main(String[] args) {
		Car car = new Car();
		FlyCar flyCar = new FlyCar(car);
		flyCar.move();
	}
}
