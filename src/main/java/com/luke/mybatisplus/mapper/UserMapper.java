package com.luke.mybatisplus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.luke.mybatisplus.pojo.User;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface UserMapper extends BaseMapper<User> {
   Map<String,Object> selectMapById(Long id);
}
