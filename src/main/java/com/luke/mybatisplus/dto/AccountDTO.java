package com.luke.mybatisplus.dto;

import lombok.Data;

@Data
public class AccountDTO {
    private String realName;
    private String email;
    private String createTimeRange;
    private Long page;
    private Long limit;
}
