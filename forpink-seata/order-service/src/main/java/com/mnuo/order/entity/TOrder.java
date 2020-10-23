package com.mnuo.order.entity;

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
@ApiModel(value="TOrder对象", description="派车单")
public class TOrder extends Model<TOrder> {
	private static final long serialVersionUID = -8891330206366476539L;
	@TableId("id")
    private Long id;
	
    @TableField("product_id")
    private Long productId;
    
    @TableField("user_id")
    private Long userId;
    
    @TableField("count")
    private Integer count;
    
    @TableField("money")
    private BigDecimal money;
    
    @TableField("status")
    private Integer status;

    @Override
    protected Serializable pkVal() {
        return null;
    }

}
