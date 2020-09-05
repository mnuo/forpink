package com.mnuo.forpink.sso.service;

import javax.validation.Valid;

import com.mnuo.forpink.core.module.Users;
import com.mnuo.forpink.sso.dto.LoginUserDto;
import com.mnuo.forpink.web.vo.Response;

public interface UserService {

	void addUser(@Valid Users userDTO);

	void deleteUser(Integer id);

	void updateUser(@Valid Users userDTO);

	Response findAllUserVO();

	Response login(LoginUserDto loginUserDTO);

}
