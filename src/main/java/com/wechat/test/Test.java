package com.wechat.test;

import com.wechat.interfaces.IPayAfterSuccess;
import com.wechat.pay.WeChatPayUtil;

public class Test {

    public static void main(String args[]){
        //测试统一下单成功后
//        WeChatPayParams params = new WeChatPayParams();
//        params.obj = new TestAfterSuccsess();
//        System.out.println("1");
//        WeChatPayUtil.wechatPayTest(params);
//        System.out.println("2");
        //测试支付成功后
        IPayAfterSuccess a =  new TestAfterSuccsess();
        System.out.println("1");
        WeChatPayUtil.wxPaymentResultTest(null,null,a);
        System.out.println("2");
    }
}
