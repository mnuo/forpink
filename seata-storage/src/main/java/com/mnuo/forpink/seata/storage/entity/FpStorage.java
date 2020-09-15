package com.mnuo.forpink.seata.storage.entity;
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
@Table(name ="fp_storage")
@NamedQuery(name="FpStorage.findAll", query="SELECT s FROM FpStorage s")
public class FpStorage implements java.io.Serializable{
    private static final long serialVersionUID = -6711533459208142848L;

    @Id
    @GeneratedValue
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name ="product_id")
    private Long productId;

    @Column(name ="total")
    private Integer total;

    @Column(name ="used")
    private Integer used;

    @Column(name ="residue")
    private Integer residue;

}
