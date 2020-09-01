package com.mnuo.forpink.auth.service;

import javax.validation.Valid;

import com.mnuo.forpink.auth.dto.LoginUserDto;
import com.mnuo.forpink.auth.module.Users;
import com.mnuo.forpink.auth.vo.Response;

public interface UserService {

	void addUser(@Valid Users userDTO);

	void deleteUser(Integer id);

	void updateUser(@Valid Users userDTO);

	Response findAllUserVO();

	Response login(LoginUserDto loginUserDTO);

}
