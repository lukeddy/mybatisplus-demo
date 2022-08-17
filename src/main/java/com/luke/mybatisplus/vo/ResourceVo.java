package com.luke.mybatisplus.vo;

import lombok.Data;

import java.util.List;

@Data
public class ResourceVo {
    private Long resourceId;
    //名称
    private String resourceName;
    //地址
    private String url;
    //子资源
    private List<ResourceVo> subs;
}
