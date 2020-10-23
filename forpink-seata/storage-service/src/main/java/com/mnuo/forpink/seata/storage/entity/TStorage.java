package com.mnuo.forpink.seata.storage.entity;

import java.io.Serializable;

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
@ApiModel(value="SendCar对象", description="派车单")
public class TStorage extends Model<TStorage> {

private static final long serialVersionUID=1L;

    @TableId("id")
    private Long id;
    @TableField("product_id")
    private Long productId;
    @TableField("total")
    private Integer total;
    @TableField("used")
    private Integer used;
    @TableField("residue")
    private Integer residue;

    @Override
    protected Serializable pkVal() {
        return null;
    }

}
