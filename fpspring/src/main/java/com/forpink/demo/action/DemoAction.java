package com.forpink.demo.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.forpink.demo.service.IDemoService;
import com.forpink.mvcframework.annotation.FPAutowired;
import com.forpink.mvcframework.annotation.FPController;
import com.forpink.mvcframework.annotation.FPRequestMapping;
import com.forpink.mvcframework.annotation.FPRequestParam;

@FPController
@FPRequestMapping("/demo")
public class DemoAction {
	@FPAutowired
	private IDemoService demoService;
	
	@FPRequestMapping("/query")
	public void query(HttpServletRequest request, HttpServletResponse response, @FPRequestParam("name") String name){
		String result = demoService.get(name);
		try {
			response.getWriter().print(result);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@FPRequestMapping("add")
	public void add(HttpServletRequest request, HttpServletResponse response, 
			@FPRequestParam("a") Integer a, @FPRequestParam("b") Integer b){
		String result = a + "+" + b + "=" + (a+b);
		try {
			response.getWriter().print(result);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	@FPRequestMapping("remove")
	public void remove(HttpServletRequest request, HttpServletResponse response, 
			@FPRequestParam("id") String id){
		System.out.println("id: " + id);
	}
}
