package com.mnuo.forpink.seata.storage.respository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.mnuo.forpink.seata.storage.entity.FpStorage;
public interface FpStorageRespository extends JpaRepository<FpStorage,Long>, JpaSpecificationExecutor<FpStorage>{
}
