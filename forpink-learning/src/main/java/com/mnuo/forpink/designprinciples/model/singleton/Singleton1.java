package com.mnuo.forpink.designprinciples.model.singleton;
/**
 * Hungry
 * 线程安全,调用效率高, 但不能延迟加载
 * @author administrator
 */
public class Singleton1 {
	private static Singleton1 instance = new Singleton1();
	
	private Singleton1(){
		
	}
	
	public static Singleton1 getInstance(){
		return instance;
	}
}
