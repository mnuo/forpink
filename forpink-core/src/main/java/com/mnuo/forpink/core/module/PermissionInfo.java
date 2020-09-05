package com.mnuo.forpink.core.module;
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
@Table(name =" permission_info")
@NamedQuery(name="PermissionInfo.findAll", query="SELECT s FROM PermissionInfo s")
public class PermissionInfo implements java.io.Serializable{
    private static final long serialVersionUID = -6684736658492882944L;

    /** 权限id */
    @Id
//    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    /** 权限名 */
    @Column(name ="name")
    private String name;

    /** 接口路径 */
    @Column(name ="path")
    private String path;

    /** 说明 */
    @Column(name ="description")
    private String description;

    /** 父id */
    @Column(name ="parent_id")
    private Long parentId;

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

    /** 角色状态'ENABLE'开启,'DISABLE'关闭 */
    @Column(name ="status")
    private String status;

    /** 请求方式 */
    @Column(name ="method")
    private String method;

}
