package com.wechat.config;

public class Content {
    /**
     * 成功
     */
    public  final  static  String  SUCCESS="SUCCESS";
    public  final  static  String  FAIL="FAIL";
    public  final  static  String  OK="OK";
    /**
     * 统一下单
     */
    public final  static  String UNIFIEDORDER="https://api.mch.weixin.qq.com/pay/unifiedorder";

    /**
     * 查询订单   使用场景（第一次支付 下单成功支付失败的时候 还要进行支付要 调用查询订单）
     */
    public final  static  String ORDERQUERY="https://api.mch.weixin.qq.com/pay/orderquery";

    /**
     * 退款
     */
    public final  static  String REFUND=" https://api.mch.weixin.qq.com/secapi/pay/refund";
    /**
     * 退款查询
     */
    public final  static  String REFUNDQUERY=" https://api.mch.weixin.qq.com/pay/refundquery";



}
