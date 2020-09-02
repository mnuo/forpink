package com.mnuo.forpink.auth.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mnuo.forpink.auth.dto.LoginUserDto;
import com.mnuo.forpink.auth.module.Users;
import com.mnuo.forpink.auth.service.RoleService;
import com.mnuo.forpink.auth.service.UserService;
import com.mnuo.forpink.auth.utils.AssertUtils;
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
@RequestMapping("/app")
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
