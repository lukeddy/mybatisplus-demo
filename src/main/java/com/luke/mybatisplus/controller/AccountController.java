package com.luke.mybatisplus.controller;

import cn.hutool.core.lang.UUID;
import cn.hutool.crypto.digest.MD5;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.luke.mybatisplus.dto.AccountDTO;
import com.luke.mybatisplus.entity.Account;
import com.luke.mybatisplus.entity.Role;
import com.luke.mybatisplus.service.AccountService;
import com.luke.mybatisplus.service.RoleService;
import com.luke.mybatisplus.utils.Constant;
import com.luke.mybatisplus.utils.ResponseUtils;
import com.luke.mybatisplus.vo.ResponseData;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
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

    @Autowired
    private RoleService roleService;


    @GetMapping("/toList")
    public String toList(){
        return "admin/account/accountList";
    }

    @GetMapping("/list")
    @ResponseBody
    public ResponseData<Map<String,Object>> listAccount(AccountDTO dto){
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

    @GetMapping("/toAdd")
    public String toAdd(Model model){
       List<Role> roleList= roleService.list(Wrappers.<Role>lambdaQuery().orderByAsc(Role::getRoleId));
       model.addAttribute("roles",roleList);
       return "admin/account/accountAdd";
    }

    @PostMapping("/doAdd")
    @ResponseBody
    public ResponseData<Object> doAddAccount(@RequestBody Account account){
        //对明文密码进行MD5加密处理
        encryptPassword(account);
        //保存数据
        boolean saveResult=accountService.save(account);
        if(saveResult){
            return ResponseData.ok(null);
        }
        return ResponseData.failed("新增账户信息失败");
    }

    private void encryptPassword(Account account){
        String password=account.getPassword();
        String salt= UUID.fastUUID().toString().replaceAll("-","");
        MD5 md5=new MD5(salt.getBytes());
        String encryptedPwd= md5.digestHex(password);
        account.setSalt(salt);
        account.setPassword(encryptedPwd);
    }

    @GetMapping("/toDetail/{id}")
    public String toDetail(@PathVariable Long id, Model model){
        Account account=accountService.getAccountById(id);
        model.addAttribute("account",account);
        return "/admin/account/accountDetail";
    }

    @GetMapping("/toUpdate/{id}")
    public String toUpdate(@PathVariable Long id, Model model){
        Account account=accountService.getById(id);
        List<Role> roleList= roleService.list(Wrappers.<Role>lambdaQuery().orderByAsc(Role::getRoleId));
        model.addAttribute("roles",roleList);
        model.addAttribute("account",account);
        return "/admin/account/accountUpdate";
    }

    @PostMapping("/doUpdate")
    @ResponseBody
    public ResponseData<Object> doUpdate(@RequestBody Account account){
        if(StringUtils.isNotBlank(account.getPassword())){
            encryptPassword(account);
        }else{
            account.setPassword(null);
        }
        boolean updateResult=accountService.updateById(account);
        if(updateResult){
            return ResponseData.ok(null);
        }
        return ResponseData.failed("更新失败");
    }

    @DeleteMapping("/doDelete/{id}")
    @ResponseBody
    public ResponseData<Object> doDelete(@PathVariable Long id, HttpSession session){
        Account account=(Account) session.getAttribute(Constant.SESSION_KEY_LOGIN_USER);
        if(account.getAccountId().equals(id)){
            return ResponseData.failed("不能删除自己！！！");
        }

        boolean delResult=accountService.removeById(id);
        if(delResult){
            return ResponseData.ok(null);
        }
        return ResponseData.failed("删除失败");
    }

    @GetMapping({"/{username}","/{username}/{accountId}"})
    @ResponseBody
    public ResponseData<Object> checkUsername(@PathVariable String username,
                                              @PathVariable(required = false)Long accountId){

        Long count=accountService.lambdaQuery()
                .eq(Account::getUsername,username)
                .ne(accountId!=null,Account::getAccountId,accountId)
                .count();
        return ResponseData.ok(count);
    }
}
