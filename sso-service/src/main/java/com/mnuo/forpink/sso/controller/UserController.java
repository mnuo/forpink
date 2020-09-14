package com.mnuo.forpink.sso.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mnuo.forpink.core.module.Users;
import com.mnuo.forpink.sso.dto.LoginUserDto;
import com.mnuo.forpink.sso.service.UserService;
import com.mnuo.forpink.web.vo.Response;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * @description 用户权限管理
 * @author Zhifeng.Zeng
 * @date 2019/4/19 13:58
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/user")
@Api(tags="用户管理")
public class UserController {
	
	@Autowired
	UserService userService;
	@ApiOperation(value="新增")
    @PostMapping("/save")
    public Response save(@RequestBody Users user){
        userService.addUser(user);
        return Response.success();
    }
	@ApiOperation(value="更新")
    @PostMapping("/update")
    public Response update(@RequestBody Users user){
        userService.updateUser(user);
        return Response.success();
    }
	@ApiOperation(value="删除")
    @GetMapping("/delete")
    public Response delete(Long id){
    	userService.deleteUser(id);
    	return Response.success();
    }
	@ApiOperation(value="列表")
    @GetMapping("/list")
    public Response list(){
    	List<Users> list = userService.findAllUserVO();
    	return Response.success(list);
    }
    

    /**
     * @description 用户注销
     * @param authorization
     * @return
     */
	@ApiOperation(value="注销")
    @GetMapping("/logout")
    public Response logout(@RequestHeader("Authorization") String authorization){
		return userService.logout(authorization);
//        return Response.success();
    }
    /**
     * @description 用户登录
     * @param loginUserDTO
     * @return
     */
	@ApiOperation(value="登录")
    @PostMapping("/login")
    public Response login(LoginUserDto loginUserDto){
        return userService.login(loginUserDto);
    }
	/**
	 * @description 用户登录
	 * @param loginUserDTO
	 * @return
	 */
	@ApiOperation(value="登录")
	@PostMapping("/login1")
	public Response login1(LoginUserDto loginUserDto){
		return userService.login(loginUserDto);
	}
	


}
