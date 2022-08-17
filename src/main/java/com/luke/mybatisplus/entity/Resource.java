package com.luke.mybatisplus.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 资源表
 * @TableName resource
 */
@TableName(value ="resource")
@Data
public class Resource implements Serializable {
    /**
     * 主键
     */
    @TableId
    private Long resourceId;

    /**
     * 父id
     */
    private Long parentId;

    /**
     * 资源名称
     */
    private String resourceName;

    /**
     * 资源类型(0、目录 1、菜单 2、按钮)
     */
    private Integer resourceType;

    /**
     * 请求地址
     */
    private String url;

    /**
     * 权限标识码
     */
    private String code;

    /**
     * 排序
     */
    private Integer sort;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}