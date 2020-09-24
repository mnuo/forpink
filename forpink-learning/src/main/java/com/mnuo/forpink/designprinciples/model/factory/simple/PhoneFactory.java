package com.mnuo.forpink.designprinciples.model.factory.simple;
/**
 * 简单工厂类违背软件开发开闭原则, 每添加一个新的子类, 都需要修改工厂类代码, 无法扩展
 * @author administrator
 */
public class PhoneFactory {
	public static Phone getInstance(String name){
		if("iphone".equals(name)){
			return new Iphone();
		}else if("xiaomi".equals(name)){
			return new Xiaomi();
		}
		return null;
	}
	
	public static void main(String[] args) {
		Phone phone = PhoneFactory.getInstance("xiaomi");
		phone.whoami();
	}
	
}
