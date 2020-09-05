package com.mnuo.forpink.sso.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mnuo.forpink.sso.dto.LoginUserDto;
import com.mnuo.forpink.sso.service.UserService;
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
@RequestMapping("/test")
public class UserController {
	
	@Autowired
	UserService userService;
	

	/**
     * @description 用户登录
     * @param loginUserDTO
     * @return
     */
    @PostMapping("user/login")
    public Response login(LoginUserDto loginUserDTO){
        return userService.login(loginUserDTO);
    }


    /**
     * @description 用户注销
     * @param authorization
     * @return
     */
    @GetMapping("user/logout")
    public Response logout(@RequestHeader("Authorization") String authorization){
//        redisTokenStore.removeAccessToken(AssertUtils.extracteToken(authorization));
        return Response.success();
    }

    /**
     * @description 获取所有角色列表
     * @return
     */
//    @GetMapping("role")
//    public Response findAllRole(){
//        return roleService.findAllRoleVO();
//    }
}
