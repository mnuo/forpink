package com.mnuo.forpink.web.vo;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mnuo.forpink.web.common.ResponseType;

import lombok.Data;

/**
 * @description:  数据格式返回统一
 * @author Zhifeng.Zeng
 * @date 2019/02/19 16:00:22
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response implements Serializable {

    private static final long serialVersionUID = -437839076132402939L;

    /**
     * 异常码
     */
    private Integer code;

    /**
     * 描述
     */
    private String message;

    /**
     * 数据
     */
    private Object data;

    public Response() {}

    public Response(Integer code, String msg) {
        this.code = code;
        this.message = msg;
    }

    public Response(ResponseType error, String msg, Object data) {
        this.code = error.getCode();
        this.message = msg;
        this.data = data;
    }

    public Response(ResponseType responseType) {
        this.code = responseType.getCode();
        this.message = responseType.getMessage();
    }

    public Response(ResponseType responseEnum, Object data) {
        this.code = responseEnum.getCode();
        this.message = responseEnum.getMessage();
        this.data = data;
    }

    public static Response success(){
        return new Response(ResponseType.SUCCESS);
    }

    public static Response success(Object data){
        return new Response(ResponseType.SUCCESS, data);
    }

    public static Response success(int code, String msg){
        return new Response(code, msg);
    }

    public static Response error(int code, String msg){
        return new Response(code,msg);
    }

    public static Response error(ResponseType responseEnum){
        return new Response(responseEnum);
    }

    public static Response error(ResponseType responseEnum, Object data){
        return new Response(responseEnum, data);
    }

    public static Response errorParams(String msg){
        return new Response(ResponseType.INCORRECT_PARAMS.getCode(), msg);
    }

    public static Response error(BindingResult result, MessageSource messageSource) {
        StringBuffer msg = new StringBuffer();
        //获取错误字段集合
        List<FieldError> fieldErrors = result.getFieldErrors();
        //获取本地locale,zh_CN
        Locale currentLocale = LocaleContextHolder.getLocale();
        //遍历错误字段获取错误消息
        for (FieldError fieldError : fieldErrors) {
            //获取错误信息
            String errorMessage = messageSource.getMessage(fieldError, currentLocale);
            //添加到错误消息集合内
            msg.append(fieldError.getField() + "：" + errorMessage + " , ");
        }
        return Response.error(ResponseType.INCORRECT_PARAMS, msg.toString());
    }

	public static Response error(String message) {
		 return new Response(ResponseType.ERROR, message, null);
	}

}
