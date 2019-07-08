package com.wechat.interfaces;

import java.util.HashMap;

/**
 * 统一下单成功后接口
 */
public interface IOrderAfterSuccess {
    /**
     * 统一下单成功后自定义处理方法
     * @param result
     */
    public void afterSuccess(HashMap<String, Object> result);
}
