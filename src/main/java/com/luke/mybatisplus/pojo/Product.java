package com.luke.mybatisplus.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_product")
public class Product {
    private Long id;
    private String name;
    private Integer price;
    private Integer version;
}
