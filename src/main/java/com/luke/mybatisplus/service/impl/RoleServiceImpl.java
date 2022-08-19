package com.luke.mybatisplus.service.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luke.mybatisplus.entity.Role;
import com.luke.mybatisplus.entity.RoleResource;
import com.luke.mybatisplus.mapper.RoleResourceMapper;
import com.luke.mybatisplus.service.RoleResourceService;
import com.luke.mybatisplus.service.RoleService;
import com.luke.mybatisplus.mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
* @author luke
* @description 针对表【role(角色表)】的数据库操作Service实现
* @createDate 2022-08-17 16:55:23
*/
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role>
    implements RoleService{

    @Resource
    private RoleResourceMapper roleResourceMapper;

    /**
     * 保存角色以及角色对应的资源信息
     * @param role
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean saveRole(Role role) {
        //保存角色信息
        save(role);
        //保存角色的对应资源信息
        Long roleId=role.getRoleId();
        List<Long> resourceIds=role.getResourceIds();
        if(CollectionUtils.isNotEmpty(resourceIds)){
            for(Long resourceId:resourceIds){
                RoleResource roleResource=new RoleResource();
                roleResource.setRoleId(roleId);
                roleResource.setResourceId(resourceId);
                roleResourceMapper.insert(roleResource);
            }
        }
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateRole(Role role) {
        updateById(role);
        Long roleId=role.getRoleId();
        //先删除角色资源关系
        roleResourceMapper.delete(Wrappers
                .<RoleResource>lambdaQuery()
                .eq(RoleResource::getRoleId,roleId));
        //再新增角色资源关系
        List<Long> resourceIds=role.getResourceIds();
        if(CollectionUtils.isNotEmpty(resourceIds)){
            for(Long resourceId:resourceIds){
                RoleResource roleResource=new RoleResource();
                roleResource.setRoleId(roleId);
                roleResource.setResourceId(resourceId);
                roleResourceMapper.insert(roleResource);
            }
        }
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean removeRole(Long roleId) {
        //先删除角色资源关系
        roleResourceMapper.delete(Wrappers
                .<RoleResource>lambdaQuery()
                .eq(RoleResource::getRoleId,roleId));
        //再删除角色本身
        baseMapper.deleteById(roleId);
        return true;
    }
}




