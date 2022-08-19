package com.luke.mybatisplus.interceptor;

import com.luke.mybatisplus.entity.Account;
import com.luke.mybatisplus.utils.Constant;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String loginURL=request.getContextPath()+"/auth/login";

        //先判断用户是否登录
        Account loginUser=(Account) request.getSession().getAttribute(Constant.SESSION_KEY_LOGIN_USER);
        if(null==loginUser){ //用户未登录，重定向到登录页
            response.sendRedirect(loginURL);
            return false;
        }
        return true;
    }
}
