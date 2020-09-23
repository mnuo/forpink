package com.mnuo.forpink.designprinciples.model.singleton;
/**
 * Double checked locking
 * 双重检查
 * 错误的写法是, 对象在实例化的三个步骤:
 * 	1分配内存空间
 * 	2初始化对象
 *  3将对象指向刚分配的内存空间
 * 有些jvm2,3步骤打乱, 导致, 对象已经指向内存空间, 但还没有初始化, 其他线程检测该对象存在直接返回
 * 解决是使用volatile关键字, 禁止重排,所有写(write)操作都发生在读(read)之前
 * @author administrator
 */
public class Singleton3 {
	private volatile static Singleton3 instance;
	
	private Singleton3(){}
	
	public static Singleton3 getInstance(){
		if(instance == null){
			synchronized (Singleton3.class) {
				if(null == instance){
					instance = new Singleton3();
				}
			}
		}
		return instance;
	}

}
