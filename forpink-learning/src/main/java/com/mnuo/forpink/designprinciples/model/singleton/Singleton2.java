package com.mnuo.forpink.designprinciples.model.singleton;
/**
 * Lazy
 * 为了线程安全, 需要添加synchronized
 * 线程安全, 调用效率不高, 可以延迟加载
 * @author administrator
 */
public class Singleton2 {
	private static Singleton2 instance;
	
	private Singleton2(){}
	
	public static synchronized Singleton2 getInstance(){
		if(instance == null){
			instance = new Singleton2();
		}
		return instance;
	}
}
