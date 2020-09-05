package com.mnuo.forpink.core.utils;

public class Assert extends org.springframework.util.Assert{
	public static void throwException(String message) {
		throw new IllegalStateException(message);
	}
}
