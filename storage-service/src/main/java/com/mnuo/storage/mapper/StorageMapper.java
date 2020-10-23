package com.mnuo.storage.mapper;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mnuo.storage.entity.TStorage;

/**
 * <p>
 * 派车单 Mapper 接口
 * </p>
 *
 * @author hanj
 * @since 2020-06-08
 */
public interface StorageMapper extends BaseMapper<TStorage> {

	void decrease(@Param("productId")Long productId, @Param("count")Integer count);

}
