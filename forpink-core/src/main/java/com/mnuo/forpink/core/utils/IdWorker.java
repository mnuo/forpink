package com.mnuo.forpink.core.utils;

public class IdWorker {
	public static long getId(){
		Snowflake snowflake = new Snowflake(0, 0);
		return snowflake.nextId();
	}
}
