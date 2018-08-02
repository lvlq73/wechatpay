package com.wechat.interfaces;

import java.util.HashMap;

/**
 * 支付成功后处理接口
 */
public interface IRefundAfterSuccess {
    /**
     * 支付成功后自定义方法
     * @param result
     */
    public void afterRefundSuccess(HashMap<String, Object> result);
}
