package com.luke.mybatisplus.vo;

import lombok.Data;

import java.util.List;

@Data
public class TreeVo {
    private String title;
    private Long id;
    private List<TreeVo> children;
    private boolean checked;
}

