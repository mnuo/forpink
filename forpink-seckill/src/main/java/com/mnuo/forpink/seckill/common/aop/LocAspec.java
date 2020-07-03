package com.mnuo.forpink.seckill.common.aop;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Scope;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author mon
 *
 */
@Component
@Order(1)
@Aspect
@Scope
@Slf4j
public class LocAspec {
	
	private Lock lock = new ReentrantLock(true);
	
	@Pointcut("@annotation(com.mnuo.forpink.seckill.common.aop.ServiceLock)")
	public void lockAspec(){
		log.info("lockAspec()....");
		
	}
	
	@Around("lockAspec()")
	public Object around(ProceedingJoinPoint joinPoint){
		lock.lock();
		Object obj = null;
		try {
			log.info("around lockAspec()");
			obj = joinPoint.proceed();
		} catch (Throwable e) {
			e.printStackTrace();
			throw new RuntimeException();
		} finally {
			lock.unlock();
		}
		return obj;
	}
	

}
