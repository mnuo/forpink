package com.mnuo.forpink.auth.dto;

import lombok.Data;

/**
 * @description 登录用户传输参数
 */
@Data
public class LoginUserDto {

    /**
     * 用户名
     */
    private String account;

    /**
     * 用户密码
     */
    private String password;

}