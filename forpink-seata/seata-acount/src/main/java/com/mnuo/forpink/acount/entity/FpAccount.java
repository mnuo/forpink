package com.mnuo.forpink.acount.entity;
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
@Table(name ="fp_account")
@NamedQuery(name="FpAccount.findAll", query="SELECT s FROM FpAccount s")
public class FpAccount implements java.io.Serializable{
    private static final long serialVersionUID = -6711533091204104192L;

    @Id
    @GeneratedValue
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name ="user_id")
    private Long userId;

    @Column(name ="total")
    private BigDecimal total;

    @Column(name ="used")
    private BigDecimal used;

    @Column(name ="residue")
    private BigDecimal residue;

}
