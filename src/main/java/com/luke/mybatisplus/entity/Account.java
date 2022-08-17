package com.luke.mybatisplus.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 账号表
 * @TableName account
 */
@TableName(value ="account")
@Data
public class Account implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long accountId;

    /**
     * 角色id
     */
    private Long roleId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 加密盐
     */
    private String salt;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 性别
     */
    private String sex;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date modifiedTime;

    /**
     * 创建人
     */
    private Long createAccountId;

    /**
     * 修改人
     */
    private Long modifiedAccountId;

    /**
     * 逻辑删除标识(0、否 1、是)
     */
    private Integer deleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}