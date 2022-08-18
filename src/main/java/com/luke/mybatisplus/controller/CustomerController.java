package com.luke.mybatisplus.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.luke.mybatisplus.entity.Customer;
import com.luke.mybatisplus.service.CustomerService;
import com.luke.mybatisplus.vo.ResponseData;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 客户表 前端控制器
 * </p>
 *
 * @author luke
 * @since 2022-08-17
 */
@Controller
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/toList")
    public String toList(){
        return "admin/customer/customerList";
    }

    @GetMapping("/list")
    @ResponseBody
    public ResponseData<Map<String,Object>> listCustomer(String realName,String phone,Long page,Long limit){
        LambdaQueryWrapper<Customer> query=new LambdaQueryWrapper<>();
        query.like(StringUtils.isNotBlank(realName),Customer::getRealName,realName)
                .like(StringUtils.isNotBlank(phone),Customer::getPhone,phone)
                .orderByDesc(Customer::getCustomerId);
        Page<Customer> myPage= customerService.page(new Page<>(page,limit),query);

        Map<String,Object> map=new HashMap<>();
        map.put("count",myPage.getTotal());
        map.put("records",myPage.getRecords());

        return  ResponseData.ok(map);
    }
}
