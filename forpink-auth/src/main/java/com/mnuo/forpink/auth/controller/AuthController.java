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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mnuo.forpink.core.module.Users;
import com.mnuo.forpink.web.vo.Response;

import lombok.extern.slf4j.Slf4j;

/**
 * @description 用户权限管理
 * @author Zhifeng.Zeng
 * @date 2019/4/19 13:58
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/auth/")
public class AuthController {

//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    private RoleService roleService;

    @Autowired
    private RedisTokenStore redisTokenStore;

    /**
     * @description 添加用户
     * @param userDTO
     * @return
     */
    @PostMapping("user")
    public Response add(@Valid @RequestBody Users userDTO) throws Exception {
//        userService.addUser(userDTO);
        return Response.success();
    }

    /**
     * @description 删除用户
     * @param id
     * @return
     */
    @DeleteMapping("user/{id}")
    public Response deleteUser(@PathVariable("id")Integer id) throws Exception {
//        userService.deleteUser(id);
        return Response.success();
    }

    /**
     * @descripiton 修改用户
     * @param userDTO
     * @return
     */
    @PutMapping("user")
    public Response updateUser(@Valid @RequestBody Users userDTO){
//        userService.updateUser(userDTO);
        return Response.success();
    }

    /**
     * @description 获取用户列表
     * @return
     */
    @GetMapping("user")
    public Response findAllUser(){
//        return userService.findAllUserVO();
    	return null;
    }
    @GetMapping("test")
    public Response test(){
    	return Response.success();
    }
}
