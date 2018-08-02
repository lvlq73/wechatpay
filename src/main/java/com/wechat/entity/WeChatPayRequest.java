package com.wechat.entity;

/**
 * 微信统一下单http请求参数实体类
 */
public class WeChatPayRequest extends BaseEntity {

    public String body;

    public String spbill_create_ip;

    public String trade_type;

    public String openid;

    public String sign;

    //public String sign_type;
}
