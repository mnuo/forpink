package com.mnuo.forpink.designprinciples.model.adapter;
/**
 * 适配器 (对象适配器方式,使用了组合的方式跟被适配对象整合)
 * @author administrator
 */
public class Adapter1 implements Target{
	public Adaptee adaptee;
	@Override
	public void newdosome() {
		adaptee.dosoming();
	}
	public Adapter1(Adaptee adaptee){
		super();
		this.adaptee = adaptee;
	}
}
