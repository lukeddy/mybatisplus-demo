package com.luke.mybatisplus.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.luke.mybatisplus.entity.Account;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.luke.mybatisplus.entity.Resource;
import org.apache.ibatis.annotations.Param;

/**
* @author luke
* @description 针对表【account(账号表)】的数据库操作Mapper
* @createDate 2022-08-17 16:55:23
* @Entity com.luke.mybatisplus.entity.Account
*/
public interface AccountMapper extends BaseMapper<Account> {

    /**
     * 分页查询用户信息
     * @param page
     * @param wrapper
     * @return
     */
    IPage<Account> selectAccountList(Page<Account> page, @Param(Constants.WRAPPER) Wrapper<Account> wrapper);

    /**
     * 根据账户ID查询信息
     * @param id
     * @return
     */
    Account selectAccountById(Long id);
}




