package com.mnuo.forpink.seata.order.entity;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name ="fp_order")
@NamedQuery(name="FpOrder.findAll", query="SELECT s FROM FpOrder s")
public class FpOrder implements java.io.Serializable{
    private static final long serialVersionUID = -6711532611128262656L;

    @Id
    @GeneratedValue
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name ="user_id")
    private Long userId;

    @Column(name ="product_id")
    private Long productId;

    @Column(name ="count")
    private Integer count;

    @Column(name ="money")
    private BigDecimal money;

    @Column(name ="status")
    private Integer status;

}
