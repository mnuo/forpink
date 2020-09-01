package com.mnuo.forpink.auth.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mnuo.forpink.auth.vo.Response;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class ExceptionHandle {
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Response handle(Exception e) {
    	log.error("系统内部错误:", e);
       return Response.error("系统内部错误" + e.getMessage());
    }
}
