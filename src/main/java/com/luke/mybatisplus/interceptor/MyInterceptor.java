package com.luke.mybatisplus.interceptor;

import com.luke.mybatisplus.utils.Constant;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;

public class MyInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI=request.getRequestURI();
        String contextPath=request.getContextPath();
        requestURI=requestURI.replaceFirst(contextPath,"");
        String moduleName=requestURI.substring(1);

        int index=moduleName.indexOf("/");
        if(index!=-1){
            moduleName=moduleName.substring(0,index);
        }
        HashSet<String> modules=(HashSet<String>) request.getSession().getAttribute(Constant.SESSION_KEY_MODULE);
        boolean canUserVisit=modules.stream().anyMatch(moduleName::equals);
        if(!canUserVisit){
            response.sendRedirect(request.getContextPath()+"/");
        }
        return canUserVisit;
    }
}
