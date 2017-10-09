package com.lyy.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by luyuanyuan on 2017/10/9.
 */
@Component
public class CtxInterceptor implements HandlerInterceptor {

    public static final String CONTEXT_NAME = "ctx";

    public static final String SCHEME_SUFFEX = "://";

    public static final String SEPARATE_SERVER_PORT = ":";

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object arg2, Exception e) throws Exception {
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object args, ModelAndView modelAndView) throws Exception {
        if (modelAndView == null || modelAndView.getModel() == null) {
            return;
        }

        StringBuffer ctx = new StringBuffer();
        ctx.append("ws");
        ctx.append(SCHEME_SUFFEX);
        ctx.append(request.getServerName());
        if (request.getServerPort() != 80) {
            ctx.append(SEPARATE_SERVER_PORT);
            ctx.append(request.getServerPort());
        }
        ctx.append(request.getContextPath());
        ctx.append("/webSocket");

        modelAndView.getModel().put(CONTEXT_NAME, ctx.toString());

        //重定向操作特殊处理
        if (modelAndView.getViewName().startsWith("redirect:")) {
            modelAndView.getModel().clear();
        }
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
        return true;
    }

}
