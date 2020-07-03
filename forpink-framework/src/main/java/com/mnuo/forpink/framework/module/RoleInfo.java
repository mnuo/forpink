package com.mnuo.forpink.framework.module;
import javax.persistence.Column;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import java.util.Date;

import lombok.Data;

@Entity
@Data
@Table(name =" role_info")
@NamedQuery(name="RoleInfo.findAll", query="SELECT s FROM RoleInfo s")
public class RoleInfo implements java.io.Serializable{
    private static final long serialVersionUID = -6684736658648072192L;

    /** 角色id */
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    /** 角色名 */
    @Column(name ="name")
    private String name;

    /**  */
    @Column(name ="create_by")
    private String createBy;

    /**  */
    @Column(name ="create_time")
    private Date createTime;

    /**  */
    @Column(name ="update_by")
    private String updateBy;

    /**  */
    @Column(name ="update_time")
    private Date updateTime;

    /** 角色等级  从小到大  1大于2 */
    @Column(name ="role_level")
    private Integer roleLevel;

    /** 启用'ENABLE'      关闭'DISABLE' */
    @Column(name ="status")
    private String status;

    /** 角色说明 */
    @Column(name ="description")
    private String description;

}
