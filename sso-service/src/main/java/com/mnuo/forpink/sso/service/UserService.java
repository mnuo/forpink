package com.mnuo.forpink.sso.service;

import java.util.List;

import javax.validation.Valid;

import com.mnuo.forpink.core.module.Users;
import com.mnuo.forpink.sso.dto.LoginUserDto;
import com.mnuo.forpink.web.vo.Response;

public interface UserService {

	void addUser(@Valid Users userDTO);

	void deleteUser(Long id);

	void updateUser(@Valid Users userDTO);

	List<Users> findAllUserVO();

	Response login(LoginUserDto loginUserDTO);

}
