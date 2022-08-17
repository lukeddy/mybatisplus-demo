package com.luke.mybatisplus.dto;

import com.luke.mybatisplus.entity.Account;
import lombok.Data;

@Data
public class LoginDTO {
    //重定向路径
    private String path;
    //错误提示信息
    private String error;
    //登录用于信息
    private Account account;
}
