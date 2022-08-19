package com.luke.mybatisplus.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.luke.mybatisplus.dto.LoginDTO;
import com.luke.mybatisplus.entity.Account;

/**
* @author luke
* @description 针对表【account(账号表)】的数据库操作Service
* @createDate 2022-08-17 16:55:23
*/
public interface AccountService extends IService<Account> {
   LoginDTO login(String username, String password);

   IPage<Account> getAccountList(Page<Account> page, Wrapper<Account> wrapper);
}
