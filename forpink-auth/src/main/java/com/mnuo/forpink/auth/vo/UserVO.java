package com.mnuo.forpink.auth.vo;

import lombok.Data;
import lombok.ToString;


/**
 * @description 用户返回参数对象
 * @author Zhifeng.Zeng
 * @date 2019/3/7
 */
@Data
@ToString
public class UserVO {

    /**
     * 用户账号
     */
    private String account;

    /**
     * 用户名
     */
    private String name;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 用户角色
     */
    private RoleVO role;


}
