package com.luke.mybatisplus.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.luke.mybatisplus.entity.Customer;
import com.luke.mybatisplus.entity.Role;
import com.luke.mybatisplus.service.ResourceService;
import com.luke.mybatisplus.service.RoleService;
import com.luke.mybatisplus.utils.ResponseUtils;
import com.luke.mybatisplus.vo.ResponseData;
import com.luke.mybatisplus.vo.TreeVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 角色表 前端控制器
 * </p>
 *
 * @author luke
 * @since 2022-08-17
 */
@Controller
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private ResourceService resourceService;


    @GetMapping("/toList")
    public String toList(){
        return "admin/role/roleList";
    }

    @GetMapping("/list")
    @ResponseBody
    public ResponseData<Map<String,Object>> listRole(String roleName, Long page, Long limit){
        LambdaQueryWrapper<Role> query=new LambdaQueryWrapper<>();
        query.like(StringUtils.isNotBlank(roleName),Role::getRoleName,roleName)
                .orderByDesc(Role::getRoleId);
        Page<Role> myPage= roleService.page(new Page<>(page,limit),query);

        return ResponseUtils.buildSuccessResponseResult(myPage);
    }

    @GetMapping("/toAdd")
    public String toAdd(){
        return "admin/role/roleAdd";
    }

    @GetMapping("/listResource")
    @ResponseBody
    public ResponseData<List<TreeVo>> listResource(){
        return ResponseData.ok(resourceService.getResourceList());
    }

    @PostMapping("/doAdd")
    @ResponseBody
    public ResponseData<Object> doAddRole(@RequestBody Role role){
        boolean saveResult=roleService.saveRole(role);
        if(saveResult){
            return ResponseData.ok(null);
        }
        return ResponseData.failed("新增角色信息失败");
    }

}
