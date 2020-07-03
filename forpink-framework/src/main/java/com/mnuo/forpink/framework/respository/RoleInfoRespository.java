package com.mnuo.forpink.framework.respository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.Column;

import com.mnuo.forpink.framework.module.RoleInfo;
@Transactional
public interface RoleInfoRespository extends JpaRepository<RoleInfo,Long>, JpaSpecificationExecutor<RoleInfo>{
}
