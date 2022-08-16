package com.luke.mybatisplus;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.luke.mybatisplus.mapper.UserMapper;
import com.luke.mybatisplus.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MyBatisPlusPluginTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testPaginationPlugin(){
        Page<User> page=new Page<>(1,3);
        QueryWrapper<User> queryWrapper=new QueryWrapper();
        userMapper.selectPage(page,null);
        System.out.println(page.getPages());
        System.out.println(page.getTotal());
        System.out.println(page.hasNext());
        System.out.println(page.hasPrevious());
        page.getRecords().forEach(System.out::println);
    }

    @Test
    public void testPaginationPlugin2(){
        Page<User> page=new Page<>(1,3);
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.like("name","Wang")
                        .gt("age",5);
        userMapper.selectPage(page,queryWrapper);
        System.out.println(page.getPages());
        System.out.println(page.getTotal());
        System.out.println(page.hasNext());
        System.out.println(page.hasPrevious());
        page.getRecords().forEach(System.out::println);
    }
}
