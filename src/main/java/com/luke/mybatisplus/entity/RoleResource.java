package com.luke.mybatisplus.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 角色资源表
 * @TableName role_resource
 */
@TableName(value ="role_resource")
@Data
public class RoleResource implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long roleResourceId;

    /**
     * 角色id
     */
    private Long roleId;

    /**
     * 资源id
     */
    private Long resourceId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}