package com.luke.mybatisplus.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

@Getter
public enum SexEnum {

    MALE(1,"男"),
    FEMALE(2,"女");

    @EnumValue
    private Integer sex;
    private String sexStr;

     SexEnum(Integer sex,String sexStr){
        this.sex=sex;
        this.sexStr=sexStr;
    }


}
