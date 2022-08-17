package com.luke.mybatisplus;

import com.luke.mybatisplus.entity.Account;
import com.luke.mybatisplus.mapper.AccountMapper;
import com.luke.mybatisplus.service.AccountService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
public class AccountTest {

    @Resource
    private AccountMapper accountMapper;

    @Resource
    private AccountService accountService;

    @Test
    public void testSelect(){
        List<Account> list=accountMapper.selectList(null);
        list.forEach(System.out::println);
    }

    @Test
    public void testList(){
        List<Account> list= accountService.list();
        list.forEach(System.out::println);
    }
}
