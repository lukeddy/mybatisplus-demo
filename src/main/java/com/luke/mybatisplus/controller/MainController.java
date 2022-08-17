package com.luke.mybatisplus.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/dashboard")
    public String home(){
        return "admin/main";
    }
}
