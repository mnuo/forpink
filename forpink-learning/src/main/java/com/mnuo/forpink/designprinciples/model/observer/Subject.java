package com.mnuo.forpink.designprinciples.model.observer;

import java.util.ArrayList;
import java.util.List;

public interface Subject {
	// 存储订阅者
	public List<Observer> list = new ArrayList<>();
	
	public void publicMessae();
	
	public void cancalMessag();
	
	public void exChange();
}
