package com.mnuo.forpink.seckill.common.aop;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 限流
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ServiceLimit {
	String description() default "";
	
	String key() default "";
	
	LimitType limitType() default LimitType.CUSTOMER;
	
	enum LimitType{
		/**
		 * 自定义
		 */
		CUSTOMER,
		/**
		 * 根据请求者ip
		 */
		IP
	}
}
