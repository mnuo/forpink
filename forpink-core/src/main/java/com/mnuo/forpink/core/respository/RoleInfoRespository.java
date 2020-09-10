package com.mnuo.forpink.core.respository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.mnuo.forpink.core.common.RolePathVO;
import com.mnuo.forpink.core.module.RoleInfo;
@Transactional
public interface RoleInfoRespository extends JpaRepository<RoleInfo,Long>, JpaSpecificationExecutor<RoleInfo>{

	@Query(" select r from RoleInfo r, UserRole u where r.id=u.roleId and u.id=:id ")
	List<RoleInfo> findRolesByUser(@Param("id") Long id);
	
	@Query(" select r from RoleInfo r, RolePermission rp, PermissionInfo p "
			+ " where r.id=rp.roleId and rp.permissionId=p.id "
			+ " 	and p.path=:url ")
	List<RoleInfo> findRolesByPermission(@Param("url") String url);
}
