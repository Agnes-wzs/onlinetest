package com.bolife.online.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.bolife.online.entity.Account;
import com.bolife.online.util.FinalDefine;

@Component
public class SecurityInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
        throws Exception {
        Account account = (Account)request.getSession().getAttribute(FinalDefine.CURRENT_ACCOUNT);
        if (account == null || account.getLevel() < 1) {
            response.sendRedirect("/404");
            return false;
        }
        return true;
    }
}
