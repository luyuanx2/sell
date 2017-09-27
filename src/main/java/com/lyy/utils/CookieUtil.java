package com.lyy.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * cookie工具类
 * Created by luyuanyuan on 2017/9/27.
 */
public class CookieUtil {

    /**
     * 设置cookie
     * @param response
     * @param name
     * @param value
     * @param maxAge
     */
    public static void set(HttpServletResponse response,
                           String name,String value,
                           int maxAge){
        Cookie cookie = new Cookie(name,value);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

    /**
     * 获取cookie
     * @param request
     * @param name
     * @return
     */
    public static Cookie get(HttpServletRequest request,String name){

        Map<String, Cookie> cookieMap = readCookie(request);

        if(cookieMap.containsKey(name)){
            return cookieMap.get(name);
        }
        return null;
    }

    private static Map<String, Cookie> readCookie(HttpServletRequest request) {
        Map<String,Cookie> map = new HashMap<>();
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie:cookies){
            map.put(cookie.getName(),cookie);
        }
        return map;
    }
}
