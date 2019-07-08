package com.wechat.entity;


import com.wechat.interfaces.IOrderAfterSuccess;

/**
 * 支付参数实体
 */
public class WeChatPayParams extends BaseEntity{

    public String key;

    public String body;

    public String spbill_create_ip;

    public String trade_type;

    public String openid;

    public IOrderAfterSuccess obj;
}
