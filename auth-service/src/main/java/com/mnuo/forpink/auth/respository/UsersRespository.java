package com.mnuo.forpink.auth.respository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.transaction.annotation.Transactional;

import com.mnuo.forpink.auth.module.Users;
@Transactional
public interface UsersRespository extends JpaRepository<Users,Long>, JpaSpecificationExecutor<Users>{
	Users findByUserName(String username);
}
