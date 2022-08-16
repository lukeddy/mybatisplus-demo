package com.luke.mybatisplus;

import com.luke.mybatisplus.pojo.User;
import com.luke.mybatisplus.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import javax.jws.soap.SOAPBinding;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class MyBatisPlusServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void testCount(){
        long count=userService.count();
        System.out.println(count);
    }

    @Test
    public void testBatchSave(){
        List<User> userList= new ArrayList<>();
        for(int i=0;i<10;i++){
            User user=new User();
            user.setName("Wangwu"+i);
            user.setEmail("wangwu"+i+"@qq.com");
            user.setAge(i);
            userList.add(user);
        }
       boolean result= userService.saveBatch(userList);
        Assert.isTrue(result,"批量添加失败");
    }
}
