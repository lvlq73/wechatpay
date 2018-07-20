package com.wechat.entity;

import com.wechat.interfaces.IOrderAfterSuccess;

/**
 * 请求参数
 */
public class WeChatPayParams {

    public String key;

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

    public IOrderAfterSuccess  obj;
}
