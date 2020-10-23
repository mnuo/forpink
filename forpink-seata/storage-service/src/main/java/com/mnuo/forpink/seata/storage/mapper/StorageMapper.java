package com.mnuo.forpink.seata.storage.mapper;

import com.mnuo.forpink.seata.storage.entity.TStorage;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

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
