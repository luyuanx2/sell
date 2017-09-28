package com.lyy.utils;


import org.apache.commons.lang3.RandomStringUtils;

/**
 * Created by 鲁源源 on 2017/9/17.
 */
public class KeyUtil {

    public static synchronized String genUniqueKey(){
        String s = RandomStringUtils.randomNumeric(6);
        long l = System.currentTimeMillis();
        return l + s;
    }

}
