package com.luke.mybatisplus.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luke.mybatisplus.entity.RoleResource;
import com.luke.mybatisplus.service.RoleResourceService;
import com.luke.mybatisplus.mapper.RoleResourceMapper;
import org.springframework.stereotype.Service;

/**
* @author luke
* @description 针对表【role_resource(角色资源表)】的数据库操作Service实现
* @createDate 2022-08-17 16:55:23
*/
@Service
public class RoleResourceServiceImpl extends ServiceImpl<RoleResourceMapper, RoleResource>
    implements RoleResourceService{

}




