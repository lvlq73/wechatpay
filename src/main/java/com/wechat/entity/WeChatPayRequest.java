package com.wechat.entity;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * 微信统一下单http请求参数实体类
 */
@JacksonXmlRootElement(localName = "xml")
public class WeChatPayRequest extends BaseEntity {

    public String body;

    public String spbill_create_ip;

    public String trade_type;

    public String openid;

    public String sign;

    //public String sign_type;
}
