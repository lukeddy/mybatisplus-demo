package com.luke.mybatisplus.enums;

import lombok.Getter;

@Getter
public enum ErrorCode {
    FAILED(-1,"操作失败"),
    SUCCESS(0,"执行成功");

    private final long code;
    private final String msg;

    ErrorCode(long code,String msg){
        this.code=code;
        this.msg=msg;
    }
}
