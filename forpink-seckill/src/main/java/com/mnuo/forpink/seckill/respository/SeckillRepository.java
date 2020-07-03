package com.mnuo.forpink.seckill.respository;

import javax.annotation.Resource;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.transaction.annotation.Transactional;

import com.mnuo.forpink.seckill.module.Seckill;

@Transactional
public interface SeckillRepository extends JpaRepository<Seckill,Long>, JpaSpecificationExecutor<Seckill> {
	
}
