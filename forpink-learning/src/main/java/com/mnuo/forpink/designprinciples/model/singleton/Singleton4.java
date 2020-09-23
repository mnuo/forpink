package com.mnuo.forpink.designprinciples.model.singleton;
/**
 * 静态内部类方式:
 * 内部类应用场景: 
 * 	场景一：当某个类除了它的外部类，不再被其他的类使用时。我们说这个内部类依附于它的外部类而存在，
 * 		可能的原因有：1、不可能为其他的类使用；
 * 				2、出于某种原因，不能被其他类引用，可能会引起错误。等等。这个场景是我们使用内部类比较多的一个场景。（内部类可以看成代码隐藏机制）
 *	场景二：当我们希望一个类必须继承多个抽象或者具体的类时，就只能用内部类来实现多重继承
 * @author administrator
 */
public class Singleton4 {
	private static class SingletonInstance{
		private static final Singleton4 instance = new Singleton4();
	}
	private Singleton4(){}
	public static Singleton4 getInstance(){
		return SingletonInstance.instance;
	}
}
