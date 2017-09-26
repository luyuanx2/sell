package com.lyy.utils;

import com.lyy.enums.CodeEnum;

/**
 * Created by luyuanyuan on 2017/9/26.
 */
public class EnumUtil {

    public static <T extends CodeEnum> T getByCode(Integer status, Class<T> enumClass) {
        for (T t : enumClass.getEnumConstants()) {
            if (status.equals(t.getCode())) {
                return t;
            }
        }
        return null;
    }
}
