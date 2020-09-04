package com.mnuo.forpink.auth.module;
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
@Table(name ="role_permission")
@NamedQuery(name="RolePermission.findAll", query="SELECT s FROM RolePermission s")
public class RolePermission implements java.io.Serializable{
    private static final long serialVersionUID = -6684736658736152576L;

    /** 角色与权限关系id */
    @Id
//    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    /** 角色id */
    @Column(name ="role_id")
    private Long roleId;

    /** 权限id */
    @Column(name ="permission_id")
    private Long permissionId;

}
