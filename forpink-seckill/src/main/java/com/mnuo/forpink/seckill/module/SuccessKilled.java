package com.mnuo.forpink.seckill.module;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import lombok.Data;


/**
 * The persistent class for the success_killed database table.
 * 
 */
@Entity
@Data
@Table(name="success_killed")
@NamedQuery(name="SuccessKilled.findAll", query="SELECT s FROM SuccessKilled s")
public class SuccessKilled implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="success_kill_id")
	private Long successKillId;

	@Column(name="create_time")
	private Date createTime;

	@Column(name="seckill_id")
	private Long seckillId;

	@Column()
	private short state;

	@Column(name="user_id")
	private Long userId;

}