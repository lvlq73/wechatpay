package com.wechat.entity;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

/**
 * 退款申请http请求参数
 */
@JacksonXmlRootElement(localName = "xml")
public class WeChatRefundRequest  extends BaseEntity{

    public String sign;

    public String out_refund_no;

    public Integer refund_fee;

    public String refund_desc;
}
