package com.luke.mybatisplus.controller;

import com.luke.mybatisplus.dto.LoginDTO;
import com.luke.mybatisplus.entity.Account;
import com.luke.mybatisplus.service.AccountService;
import com.luke.mybatisplus.service.ResourceService;
import com.luke.mybatisplus.utils.Constant;
import com.luke.mybatisplus.vo.ResourceVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.List;

@Controller
@RequestMapping("/auth")
public class LoginController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private ResourceService resourceService;

    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }

    @PostMapping("/login")
    public String login(String username, String password, HttpSession session, RedirectAttributes redirectAttributes){
        LoginDTO loginDTO=accountService.login(username,password);
        String error=loginDTO.getError();
        if(error==null){
            Account account=loginDTO.getAccount();
            session.setAttribute(Constant.SESSION_KEY_LOGIN_USER,account);
            List<ResourceVo> resourceVoList=resourceService.getResourceByRoleId(account.getRoleId());
            session.setAttribute("resourceVoList",resourceVoList);

            //将当前用户能访问的资源模块存到集合并保存到session中
            HashSet<String> modules=resourceService.convert(resourceVoList);
            session.setAttribute(Constant.SESSION_KEY_MODULE,modules);
        }else{
            redirectAttributes.addFlashAttribute("error",error);
        }
        return loginDTO.getPath();
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/";
    }
}
