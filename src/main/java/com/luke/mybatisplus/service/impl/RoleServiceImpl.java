package com.luke.mybatisplus.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luke.mybatisplus.entity.Role;
import com.luke.mybatisplus.service.RoleService;
import com.luke.mybatisplus.mapper.RoleMapper;
import org.springframework.stereotype.Service;

/**
* @author luke
* @description 针对表【role(角色表)】的数据库操作Service实现
* @createDate 2022-08-17 16:55:23
*/
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role>
    implements RoleService{

}




