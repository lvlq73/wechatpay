package com.wechat.utils;


import com.wechat.enums.BaseEnum;
import com.wechat.entity.ResultVo;

public class ResultVoUtil {

    public static ResultVo success(Object object) {
        ResultVo resultVO = new ResultVo();
        resultVO.setData(object);
        resultVO.setSuccess(true);
        resultVO.setCode(0);
        resultVO.setMessage("成功");
        return resultVO;
    }
    public static ResultVo success() {
        ResultVo resultVO = new ResultVo();
        resultVO.setData(null);
        resultVO.setSuccess(true);
        resultVO.setCode(0);
        resultVO.setMessage("成功");
        return resultVO;
    }

    public static ResultVo fail(BaseEnum menu) {
        ResultVo resultVO = new ResultVo();
        resultVO.setData(null);
        resultVO.setSuccess(false);
        resultVO.setCode(menu.getCode());
        resultVO.setMessage(menu.getMessage());
        return resultVO;
    }


    public static ResultVo result(BaseEnum menu, Object data, boolean success) {
        ResultVo resultVO = new ResultVo();
        resultVO.setData(data);
        resultVO.setCode(menu.getCode());
        resultVO.setSuccess(success);
        resultVO.setMessage(menu.getMessage());
        return resultVO;
    }

    public static ResultVo fail(Integer code,String message) {
        ResultVo resultVO = new ResultVo();
        resultVO.setData(null);
        resultVO.setSuccess(false);
        resultVO.setCode(code);
        resultVO.setMessage(message);
        return resultVO;
    }
}
