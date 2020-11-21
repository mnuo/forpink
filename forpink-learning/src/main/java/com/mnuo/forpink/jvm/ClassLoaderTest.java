package com.mnuo.forpink.jvm;

import java.util.HashMap;
import java.util.Map;

public class ClassLoaderTest {
	public static Map map = new HashMap(){{map.put("a","2");}};
	public static void main(String[] args) {
//		Integer isInt = (Integer) map.get("a");
		System.out.println(map.get("a"));
	}
}
