package com.luke.mybatisplus.interceptor;

import com.luke.mybatisplus.utils.Constant;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;

@Component
public class PermissionInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String dashboardURL=request.getContextPath()+"/dashboard";
        //然后判断是否能有权限访问
        String moduleName=parseModuleNameFromUri(request);
        HashSet<String> modules=(HashSet<String>) request.getSession().getAttribute(Constant.SESSION_KEY_MODULE);
        boolean canUserVisit=modules.stream().anyMatch(moduleName::equals);
        if(!canUserVisit){
            response.sendRedirect(dashboardURL);
            return false;
        }
        return true;
    }

    /**
     * 从URI中解析出模块名称
     * @param request
     * @return
     */
    private String parseModuleNameFromUri(HttpServletRequest request){
        String requestURI=request.getRequestURI();
        String contextPath=request.getContextPath();
        requestURI=requestURI.replaceFirst(contextPath,"");
        String moduleName=requestURI.substring(1);

        int index=moduleName.indexOf("/");
        if(index!=-1){
            moduleName=moduleName.substring(0,index);
        }
        return moduleName;
    }
}
