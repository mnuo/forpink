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
@Table(name =" user_role")
@NamedQuery(name="UserRole.findAll", query="SELECT s FROM UserRole s")
public class UserRole implements java.io.Serializable{
    private static final long serialVersionUID = -6684736658849398784L;

    /** 用户与角色关系id */
    @Column(name ="id")
    private Long id;

    /** 用户id */
    @Column(name ="user_id")
    private Long userId;

    /** 角色id */
    @Column(name ="role_id")
    private Long roleId;

}
