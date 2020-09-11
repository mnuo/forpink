package com.mnuo.forpink.auth.domain;

import java.util.List;

import lombok.Builder;
import lombok.Data;

/**
 * @author Zhifeng.Zeng
 * @description oauth2客户端token参数
 * @date 2019/3/8
 */
@Builder
@Data
public class Token {

    /**
     * 过期时间
     */
    private String expiration;
    /**
     * 是否过期
     */
    private boolean expired;
    /**
     * 过期时限
     */
    private int expiresIn;
    /**
     * refreshToken对象
     */
    private String refreshToken;

    /**
     * token类型
     */
    private String tokenType;

    /**
     * access_token值
     */
    private String value;
    /**
     * 访问令牌头前缀
     */
    private String tokenHead;

    /**
     * 使用范围
     */
    private List<String> scope;
}
