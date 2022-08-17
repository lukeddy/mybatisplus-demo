package com.luke.mybatisplus.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luke.mybatisplus.entity.Customer;
import com.luke.mybatisplus.service.CustomerService;
import com.luke.mybatisplus.mapper.CustomerMapper;
import org.springframework.stereotype.Service;

/**
* @author luke
* @description 针对表【customer(客户表)】的数据库操作Service实现
* @createDate 2022-08-17 16:55:23
*/
@Service
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer>
    implements CustomerService{

}




