package com.wechat.entity;

/**
 * 微信统一下单请求参数实体类
 */
public class WeChatPayRequest {

    public String appid;

    public String mch_id;

    public String nonce_str;

    public String body;

    public String out_trade_no;

    public int total_fee;

    public String spbill_create_ip;

    public String notify_url;

    public String trade_type;

    public String openid;

    public String sign;

    //public String sign_type;
}
