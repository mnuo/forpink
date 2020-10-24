package com.mnuo.forpink.fromaccount.mapper;

import java.math.BigDecimal;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mnuo.forpink.fromaccount.entity.TAccount;

/**
 * <p>
 * </p>
 *
 * @author hanj
 * @since 2020-06-08
 */
public interface AccountMapper extends BaseMapper<TAccount> {

	void decrease(@Param("userId") Long userId, @Param("money")BigDecimal money);

}
