package com.mnuo.forpink.auth.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

/**
 * @description 角色返回参数对象
 * @author Zhifeng.Zeng
 * @date 2019/3/7
 */
@Data
@ToString
@AllArgsConstructor
public class RolePathVO {
    /**
     * 角色名(中文)
     */
    private String role;

    /**
     * 角色名
     */
    private String path;
}
