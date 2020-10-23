package com.mnuo.order.mapper;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mnuo.order.entity.TOrder;

/**
 * <p>
 * </p>
 *
 * @author hanj
 * @since 2020-06-08
 */
public interface OrderMapper extends BaseMapper<TOrder> {
	/**
     * 创建订单
     */
    void create(TOrder order);

    /**
     * 修改订单金额
     */
    void update(@Param("userId") Long userId, @Param("status") Integer status);
}
