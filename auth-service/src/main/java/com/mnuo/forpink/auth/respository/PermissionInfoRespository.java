package com.mnuo.forpink.auth.respository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.transaction.annotation.Transactional;

import com.mnuo.forpink.auth.module.PermissionInfo;
@Transactional
public interface PermissionInfoRespository extends JpaRepository<PermissionInfo,Long>, JpaSpecificationExecutor<PermissionInfo>{
}
