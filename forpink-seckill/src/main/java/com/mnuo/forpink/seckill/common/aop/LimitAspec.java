package com.mnuo.forpink.seckill.common.aop;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Scope;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.RateLimiter;
import com.mnuo.forpink.seckill.common.aop.ServiceLimit.LimitType;
import com.mnuo.forpink.seckill.common.utils.IPUtils;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
@Order(2)
@Scope
public class LimitAspec {
	private static LoadingCache<String, RateLimiter> caches = CacheBuilder.newBuilder()
			.maximumSize(1000)
			.expireAfterWrite(1, TimeUnit.DAYS)
			.build(new CacheLoader<String, RateLimiter>(){
				@Override
				public RateLimiter load(String key) throws Exception {
					return RateLimiter.create(5);
				}
			});
	
	@Pointcut("@annotation(com.mnuo.forpink.seckill.common.aop.ServiceLimit)")
	public void limitAspec(){
		
	}
	
	@Around("limitAspec()")
	public Object around(ProceedingJoinPoint joinPoint){
		MethodSignature signature = (MethodSignature)joinPoint.getSignature();
		Method mothod = signature.getMethod();
		ServiceLimit limitAnnotation = mothod.getAnnotation(ServiceLimit.class);
		LimitType limitType = limitAnnotation.limitType();
		String key = limitAnnotation.key();
		Object object = null;
		try {
			if(limitType.equals(ServiceLimit.LimitType.IP)){
				key = IPUtils.getIpAddr();
			}
			RateLimiter rateLimiter = caches.get(key);
			boolean flag = rateLimiter.tryAcquire();
			if(flag){
				object = joinPoint.proceed();
			}else{
				log.info("小同志，你访问的太频繁了");
//				throw new Exception("小同志，你访问的太频繁了");
			}
		} catch (Throwable e) {
			e.printStackTrace();
//			throw new RuntimeException("小同志，你访问的太频繁了");
		}
		
		return object;
		
	}
}
