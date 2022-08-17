package com.luke.mybatisplus.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luke.mybatisplus.entity.Account;
import com.luke.mybatisplus.service.AccountService;
import com.luke.mybatisplus.mapper.AccountMapper;
import org.springframework.stereotype.Service;

/**
* @author luke
* @description 针对表【account(账号表)】的数据库操作Service实现
* @createDate 2022-08-17 16:55:23
*/
@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account>
    implements AccountService{

}




