package com.lyy.utils;

import com.lyy.vo.ResultVo;

/**
 * Created by 鲁源源 on 2017/9/17.
 */
public class ResultVOUtil {

    public static ResultVo success(Object o){
        ResultVo<Object> resultVo = new ResultVo<>();
        resultVo.setData(o);
        resultVo.setCode(0);
        resultVo.setMsg("成功");
        return resultVo;
    }

    public static ResultVo success(){
        return success(null);
    }

    public static ResultVo error(Integer code,String msg){
        ResultVo resultVo = new ResultVo();
        resultVo.setMsg(msg);
        resultVo.setCode(code);
        return resultVo;
    }

}
