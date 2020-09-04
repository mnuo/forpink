package com.mnuo.forpink.auth.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mnuo.forpink.auth.vo.Response;

import lombok.extern.slf4j.Slf4j;

/**
 * @description 用户权限管理
 * @author Zhifeng.Zeng
 * @date 2019/4/19 13:58
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/test")
public class UserController {

    /**
     * @description 添加用户
     * @param userDTO
     * @return
     */
    @GetMapping("/user")
    public Response add() throws Exception {
        return Response.success();
    }



}
