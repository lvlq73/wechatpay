package com.wechat.pay;

import com.wechat.config.Content;
import com.wechat.entity.ResultVo;
import com.wechat.entity.WeChatPayParams;
import com.wechat.entity.WeChatPayRequest;
import com.wechat.interfaces.IPayAfterSuccess;
import com.wechat.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;

/**
 * 微信支付工具类
 */
public class WeChatPayUtil {
    public static Logger logger = LoggerFactory.getLogger(WeChatPayUtil.class);
    /**
     * 统一下单
     * @param params
     * @return
     */
    public static ResultVo wechatPay(WeChatPayParams params){
        try {
            //加密获取sign
            HashMap<String,String> signMap = new HashMap<String,String>();
            signMap.put("appid",params.appid);
            signMap.put("mch_id",params.mch_id);
            signMap.put("nonce_str",params.nonce_str);
            signMap.put("body",params.body);
            signMap.put("out_trade_no",params.out_trade_no);
            signMap.put("total_fee", StringUtil.toString(params.total_fee));
            signMap.put("spbill_create_ip",params.spbill_create_ip);
            signMap.put("notify_url",params.notify_url);
            signMap.put("trade_type",params.trade_type);
            signMap.put("openid",params.openid);
            //signMap.put("sign_type","MD5");
            String sign = WeChatUtil.getSign(signMap,params.key);
            //拼装请求参数
            WeChatPayRequest entity = new WeChatPayRequest();
            entity.appid = params.appid;
            entity.mch_id = params.mch_id;
            entity.nonce_str = params.nonce_str;
            entity.body = params.body;
            entity.out_trade_no = params.out_trade_no;
            entity.total_fee = params.total_fee;
            entity.spbill_create_ip = params.spbill_create_ip;
            entity.notify_url = params.notify_url;
            entity.trade_type = params.trade_type;
            entity.openid = params.openid;
            entity.sign = sign;
            String xmlStr = ConvertUtil.objectToXml(entity).replace("__","_");
            // System.out.println(xmlStr);
            String responseStr = HttpUtil.httpResFulReturnString(Content.UNIFIEDORDER,"POST","text/xml;charset=utf-8","text/xml;charset=utf-8",xmlStr,false);

                responseStr = new String(responseStr.getBytes("ISO-8859-1"),"utf-8");
            // System.out.println(responseStr);
            HashMap<String,Object> result = ConvertUtil.readStringXmlOut(responseStr);
            if(Content.SUCCESS.equals(result.get("return_code"))&&Content.SUCCESS.equals(result.get("result_code"))){
                //返回的对象
                HashMap<String,Object> returnObj = new HashMap<String,Object>();
                //自定义处理方法
                if(params.obj !=null){
                    params.obj.afterSuccess(result);
                }
                // 支付 准备ID
                String prepayId=String.valueOf(result.get("prepay_id"));
                Long time = new Date().getTime();
                String nonceStr = WeChatUtil.getRandomString(32);
                returnObj.put("timeStamp",time);
                returnObj.put("nonceStr",nonceStr);
                returnObj.put("package","prepay_id="+result.get("prepay_id"));
                returnObj.put("signType","MD5");
                //paySign签名算法
                HashMap<String,String> paySignMap = new HashMap<String,String>();
                paySignMap.put("appId",params.appid);
                paySignMap.put("nonceStr",nonceStr);
                paySignMap.put("package","prepay_id="+prepayId);
                paySignMap.put("signType","MD5");
                paySignMap.put("timeStamp",StringUtil.toString(time));
                String paySign = WeChatUtil.getSign(paySignMap,params.key);
                returnObj.put("paySign",paySign);
                return  ResultVoUtil.success(returnObj);
            }
            return  ResultVoUtil.fail(9999,"下单失败");
        } catch (UnsupportedEncodingException e) {
            return  ResultVoUtil.fail(9999,"下单失败："+e.getMessage());
        }
    }

    /**
     * 支付成功后回调方法
     * @param response
     * @param request
     * @param iPayAfterSuccess（自定义结果处理方法）
     * @return
     */
    public static String  wxPaymentResult(HttpServletResponse response, HttpServletRequest request, IPayAfterSuccess iPayAfterSuccess){
        String xmlResult = WeChatUtil.getWeChatResponse(request);
        //System.out.println(xmlResult);
        HashMap<String,Object> result = ConvertUtil.readStringXmlOut(xmlResult);
        //支付结果通知数据处理
        StringBuilder confirmResult = new StringBuilder();
        try{
            if(iPayAfterSuccess !=null){
                iPayAfterSuccess.afterPaySuccess(result);
            }
            confirmResult.append("<xml>");
            confirmResult.append("<return_code><![CDATA[SUCCESS]]></return_code>");
            confirmResult.append("<return_msg><![CDATA[OK]]></return_msg>");
            confirmResult.append("</xml> ");
            return confirmResult.toString();
        }catch (Exception e){
            logger.error(e.getMessage());
            confirmResult.append("<xml>");
            confirmResult.append("<return_code><![CDATA[FAIL]]></return_code>");
            confirmResult.append("<return_msg><![CDATA[订单支付处理异常]]></return_msg>");
            confirmResult.append("</xml> ");
            return confirmResult.toString();
        }
    }

    @Deprecated
    public static void wechatPayTest(WeChatPayParams params){
        //自定义处理方法
        if(params.obj !=null){
            HashMap<String, Object> test = new HashMap<String, Object>();
            test.put("test","test");
            params.obj.afterSuccess(test);
        }
    }

    @Deprecated
    public static void wxPaymentResultTest(HttpServletResponse response, HttpServletRequest request, IPayAfterSuccess iPayAfterSuccess){
        //自定义处理方法
        if(iPayAfterSuccess !=null){
            HashMap<String, Object> test = new HashMap<String, Object>();
            test.put("test","afterPaySuccess");
            iPayAfterSuccess.afterPaySuccess(test);
        }
    }
}
