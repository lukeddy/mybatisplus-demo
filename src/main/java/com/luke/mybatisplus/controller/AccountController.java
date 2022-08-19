package com.luke.mybatisplus.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.luke.mybatisplus.dto.AccountDTO;
import com.luke.mybatisplus.entity.Account;
import com.luke.mybatisplus.service.AccountService;
import com.luke.mybatisplus.utils.ResponseUtils;
import com.luke.mybatisplus.vo.ResponseData;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * <p>
 * 账号表 前端控制器
 * </p>
 *
 * @author luke
 * @since 2022-08-17
 */
@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;
    @GetMapping("/toList")
    public String toList(){
        return "admin/account/accountList";
    }

    @GetMapping("/list")
    @ResponseBody
    public ResponseData<Map<String,Object>> listCustomer(AccountDTO dto){
        QueryWrapper<Account> query=new QueryWrapper<>();
        query.like(StringUtils.isNotBlank(dto.getRealName()),"a.real_name",dto.getRealName())
             .like(StringUtils.isNotBlank(dto.getEmail()),"a.email",dto.getEmail());
        String createTimeRange=dto.getCreateTimeRange();
        if(StringUtils.isNotBlank(createTimeRange)){
            String []timeRange=createTimeRange.split(" - ");
            query.ge("a.create_time",timeRange[0])
                    .le("a.create_time",timeRange[1]);
        }
        query.eq("a.deleted",0).orderByDesc("a.account_id");

        IPage<Account> myPage=accountService.getAccountList(new Page(dto.getPage(),dto.getLimit()),query);
        return ResponseUtils.buildSuccessResponseResult(myPage);
    }
}
