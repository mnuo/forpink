package com.mnuo.forpink.designprinciples.openclosed;

public class JavaDiscountCourse extends JavaCourse {
	@Override
	public String getPrice() {
		return (price*0.61)+"";
	}
}
