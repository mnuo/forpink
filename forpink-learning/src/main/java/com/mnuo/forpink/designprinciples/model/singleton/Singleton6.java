package com.mnuo.forpink.designprinciples.model.singleton;

import java.io.ObjectStreamException;

/**
 * @author administrator
 */
public class Singleton6 {
	private static Singleton6 instance = null;
	private Singleton6(){
		if(instance != null){
			throw new RuntimeException("");
		}
	}
	public static synchronized  Singleton6 getInstance(){
		if(instance == null){
			instance = new Singleton6();
		}
		return instance;
	}
	private Object readResolve() throws ObjectStreamException{
		return instance;
	}
	public static void main(String[] args)  {
		String a = "hello2";  
		final String b = "hello";
		String d = "hello";
		String c = b + 2;  
		String e = d + 2;
		System.out.println((a == c));
		System.out.println((a == e));
	}
}
