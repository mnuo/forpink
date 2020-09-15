package com.mnuo.forpink.acount.respository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.mnuo.forpink.acount.entity.FpAccount;

public interface FpAccountRespository extends JpaRepository<FpAccount,Long>, JpaSpecificationExecutor<FpAccount>{
}
