package com.mnuo.forpink.framework.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.mnuo.forpink.framework.module.Users;
import com.mnuo.forpink.framework.respository.UsersRespository;
import com.mnuo.forpink.framework.service.IUserSerivce;

@Service
public class UserServiceImpl implements IUserSerivce {

	@Autowired
	UsersRespository usersRespository;
	
	@Override
	public Object findAll() {
		List<Users> users = usersRespository.findAll();
		
		
		return users;
	}

}
