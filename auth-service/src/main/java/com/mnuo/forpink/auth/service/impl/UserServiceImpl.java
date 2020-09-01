package com.mnuo.forpink.auth.service.impl;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.mnuo.forpink.auth.dto.LoginUserDto;
import com.mnuo.forpink.auth.module.Users;
import com.mnuo.forpink.auth.service.UserService;
import com.mnuo.forpink.auth.vo.Response;

@Service
public class UserServiceImpl implements UserService{

	@Override
	public void addUser(@Valid Users userDTO) {
		
		
	}

	@Override
	public void deleteUser(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateUser(@Valid Users userDTO) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Response findAllUserVO() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response login(LoginUserDto loginUserDTO) {
		// TODO Auto-generated method stub
		return null;
	}

}
