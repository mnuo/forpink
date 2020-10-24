package com.mnuo.forpink.toaccount.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 派车单
 * </p>
 *
 * @author hanj
 * @since 2020-06-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="TAccount对象", description="派车单")
public class TAccount extends Model<TAccount> {
	private static final long serialVersionUID = -5562776403782735681L;

	@TableId("id")
    private Long id;
	
    @TableField("user_id")
    private Long userId;
    
    @TableField("total")
    private BigDecimal total;
    
    @TableField("used")
    private BigDecimal used;
    
    @TableField("residue")
    private BigDecimal residue;

    @Override
    protected Serializable pkVal() {
        return null;
    }

}
