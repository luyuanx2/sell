package com.lyy.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by luyuanyuan on 2017/9/25.
 */
public class JsonUtil {

    /**
     * 对象转json字符串
     * @param Object
     * @return
     */
    public static String toJson(Object Object){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        Gson gson = gsonBuilder.create();
        return gson.toJson(Object);
    }


}
