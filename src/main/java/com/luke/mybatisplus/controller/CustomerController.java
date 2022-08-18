package com.luke.mybatisplus.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.luke.mybatisplus.entity.Customer;
import com.luke.mybatisplus.service.CustomerService;
import com.luke.mybatisplus.utils.ResponseUtils;
import com.luke.mybatisplus.vo.ResponseData;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

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

        return ResponseUtils.buildSuccessResponseResult(myPage);
    }

    @GetMapping("/toAdd")
    public String toAdd(){
        return "admin/customer/customerAdd";
    }

    @PostMapping("/doAdd")
    @ResponseBody
    public ResponseData<Object> doAddCustomer(@RequestBody Customer customer){
        boolean saveResult=customerService.save(customer);
        if(saveResult){
            return ResponseData.ok(null);
        }
        return ResponseData.failed("新增客户信息失败");
    }

    @GetMapping("/toUpdate/{id}")
    public String toUpdate(@PathVariable Long id, Model model){
        Customer customer=customerService.getById(id);
        model.addAttribute("customer",customer);
        return "/admin/customer/customerUpdate";
    }

    @PostMapping("/doUpdate")
    @ResponseBody
    public ResponseData<Object> doUpdate(@RequestBody Customer customer){
        boolean updateResult=customerService.updateById(customer);
        if(updateResult){
            return ResponseData.ok(null);
        }
        return ResponseData.failed("更新失败");
    }

    @DeleteMapping("/doDelete/{id}")
    @ResponseBody
    public ResponseData<Object> doDelete(@PathVariable Long id){
        boolean delResult=customerService.removeById(id);
        if(delResult){
            return ResponseData.ok(null);
        }
        return ResponseData.failed("删除失败");
    }
}
