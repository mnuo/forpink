package com.mnuo.forpink.seata.order.respository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.mnuo.forpink.seata.order.entity.FpOrder;

public interface FpOrderRespository extends JpaRepository<FpOrder,Long>, JpaSpecificationExecutor<FpOrder>{
}
