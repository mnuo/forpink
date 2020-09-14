package com.mnuo.forpink.sso.service;

import java.util.List;

import com.mnuo.forpink.core.module.Users;
import com.mnuo.forpink.sso.domain.Token;
import com.mnuo.forpink.sso.dto.LoginUserDto;
import com.mnuo.forpink.web.vo.Response;

public interface UserService {

	void addUser( Users userDTO);

	void deleteUser(Long id);

	void updateUser( Users userDTO);

	List<Users> findAllUserVO();

	Response login(LoginUserDto loginUserDTO);

	Response logout(String authorization);
	Token oauthRefreshToken(String refreshToken);
}
