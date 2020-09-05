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
@Table(name =" users")
@NamedQuery(name="Users.findAll", query="SELECT s FROM Users s")
public class Users implements java.io.Serializable{
    private static final long serialVersionUID = -6684736658996199424L;

    /** 用户主键 */
    @Id
//    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    /** 用户名 */
    @Column(name ="user_name")
    private String userName;

    /** 密码 */
    @Column(name ="pass_word")
    private String passWord;

    /** 用户头像地址 */
    @Column(name ="avatar")
    private String avatar;

    /** 手机号 */
    @Column(name ="mobile")
    private Integer mobile;

    /** 邮箱 */
    @Column(name ="email")
    private String email;

    /** 用户昵称 */
    @Column(name ="nick_name")
    private String nickName;

    /** 是否生效 */
    @Column(name ="status")
    private String status;

}
