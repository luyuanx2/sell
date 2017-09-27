package com.lyy.controller;

import com.lyy.config.ProjectUrlConfig;
import com.lyy.constant.CookieConstant;
import com.lyy.constant.RedisConstant;
import com.lyy.domain.SellerInfo;
import com.lyy.enums.ResultCode;
import com.lyy.service.SellerInfoService;
import com.lyy.utils.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Created by luyuanyuan on 2017/9/27.
 */
@Controller
@RequestMapping("/seller/")
public class SellerUserController {

    @Autowired
    private SellerInfoService sellerInfoService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private ProjectUrlConfig projectUrlConfig;

    /**
     * 登录
     * @param openid
     * @param response
     * @return
     */
    @GetMapping("login")
    public ModelAndView login(@RequestParam String openid, HttpServletResponse response){

        SellerInfo sellerInfo = sellerInfoService.findByOpenid(openid);
        Map<String,Object> map = new HashMap<>();
        //登录失败
        if(sellerInfo == null){
            map.put("msg", ResultCode.LOGIN_FAIL.getMessage());
            map.put("url", "/sell/seller/order/list");
            return new ModelAndView("common/error");
        }

        String token = UUID.randomUUID().toString();
        //token设置到redis
        redisTemplate.opsForValue().set(String.format(RedisConstant.TOKEN_PREFIX,token),openid, CookieConstant.EXPIRE, TimeUnit.SECONDS);

        //将token设置到cookie
        CookieUtil.set(response,CookieConstant.TOKEN,token,CookieConstant.EXPIRE);

        return new ModelAndView("redirect:" + projectUrlConfig.getSell() + "/sell/seller/order/list");
    }

    /**
     * 登出
     * @param response
     * @param request
     * @return
     */
    @GetMapping("logout")
    public ModelAndView logout(HttpServletResponse response, HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
        if(cookie != null){
            redisTemplate.opsForValue().getOperations().delete(String.format(RedisConstant.TOKEN_PREFIX,cookie.getValue()));

            CookieUtil.set(response,CookieConstant.TOKEN,null,0);
        }

        map.put("msg", ResultCode.LOGOUT_SUCCESS.getMessage());
        map.put("url", "/sell/seller/order/list");
        return new ModelAndView("common/success", map);
    }

}
