package com.mnuo.forpink.auth.respository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.mnuo.forpink.auth.module.RoleInfo;
@Transactional
public interface RoleInfoRespository extends JpaRepository<RoleInfo,Long>, JpaSpecificationExecutor<RoleInfo>{

	@Query(" select r from RoleInfo r, UserRole u where r.id=u.roleId and u.id=:id ")
	List<RoleInfo> findRolesByUser(@Param("id") Long id);
}
