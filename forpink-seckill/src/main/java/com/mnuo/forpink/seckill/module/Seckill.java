package com.mnuo.forpink.seckill.module;

import java.io.Serializable;
import javax.persistence.*;

import lombok.Data;

import java.util.Date;


/**
 * The persistent class for the seckill database table.
 * 
 */
@Entity
@Data
@Table(name="seckill")
@NamedQuery(name="Seckill.findAll", query="SELECT s FROM Seckill s")
public class Seckill implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="seckill_id", unique=true, nullable=false)
	private Long seckillId;

	@Column(name="create_time", nullable=false)
	private Date createTime;

	@Column(name="end_time", nullable=false)
	private Date endTime;

	@Column(nullable=false, length=120)
	private String name;

	@Column(nullable=false)
	private int number;

	@Column(name="start_time", nullable=false)
	private Date startTime;

	@Column(nullable=false)
	private int version;

}