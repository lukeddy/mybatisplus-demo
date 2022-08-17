package com.luke.mybatisplus.service.impl;

import cn.hutool.crypto.digest.MD5;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luke.mybatisplus.dto.LoginDTO;
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

    @Override
    public LoginDTO login(String username, String password) {
        LoginDTO loginDTO=new LoginDTO();
        loginDTO.setPath("redirect:/");

        Account account=lambdaQuery().eq(Account::getUsername,username).one();
        if(null==account){
            loginDTO.setError("用户名不存在");
            return loginDTO;
        }
      MD5 md5= new MD5(account.getSalt().getBytes());
      String digestHex=md5.digestHex(password);
      if(!digestHex.equals(account.getPassword())){
          loginDTO.setError("密码错误");
          return loginDTO;
      }

      loginDTO.setPath("redirect:/dashboard");
      loginDTO.setAccount(account);
      return loginDTO;
    }
}




