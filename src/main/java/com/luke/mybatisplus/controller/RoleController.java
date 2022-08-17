package com.luke.mybatisplus.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;

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

    @GetMapping("/toList")
    public String toList(){
        return "admin/role/roleList";
    }

}
