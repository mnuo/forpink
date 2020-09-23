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
}
