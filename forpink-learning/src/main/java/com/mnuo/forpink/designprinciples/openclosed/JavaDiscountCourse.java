package com.mnuo.forpink.designprinciples.openclosed;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class JavaDiscountCourse extends JavaCourse {
	@Override
	public String getPrice() {
		return (price*0.61)+"";
	}

}
