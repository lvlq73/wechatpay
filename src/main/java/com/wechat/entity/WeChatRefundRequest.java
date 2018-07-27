package com.wechat.entity;

/**
 * 退款申请http请求参数
 */
public class WeChatRefundRequest  extends BaseEntity{

    public  String sign;

    public  String out_refund_no;

    public  Integer refund_fee;
}
