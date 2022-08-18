package com.luke.mybatisplus.utils;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.luke.mybatisplus.vo.ResponseData;

import java.util.HashMap;
import java.util.Map;

/**
 * 同意封装响应结果
 */
public class ResponseUtils {
    public static ResponseData<Map<String,Object>> buildSuccessResponseResult(IPage<?> page){
        Map<String,Object> map=new HashMap<>();
        map.put("count",page.getTotal());
        map.put("records",page.getRecords());
        return  ResponseData.ok(map);
    }
}
