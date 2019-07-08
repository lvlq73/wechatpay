package com.wechat.pay;

import com.alibaba.fastjson.JSON;
import com.wechat.config.Content;
import com.wechat.entity.*;
import com.wechat.enums.RefundExceptionEmnu;
import com.wechat.interfaces.IPayAfterSuccess;
import com.wechat.interfaces.IRefundAfterSuccess;
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
            HashMap<String,Object> signMap = new HashMap<String,Object>();
            signMap.put("appid",params.appid);
            signMap.put("mch_id",params.mch_id);
            signMap.put("nonce_str",params.nonce_str);
            signMap.put("body",params.body);
            signMap.put("out_trade_no",params.out_trade_no);
            signMap.put("total_fee", Usual.f_getString(params.total_fee));
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
            String xmlStr = ConvertUtil.objectToXml(entity);
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
                String prepayId= String.valueOf(result.get("prepay_id"));
                Long time = new Date().getTime();
                String nonceStr = WeChatUtil.getRandomString(32);
                returnObj.put("timeStamp",Usual.f_getString(time));
                returnObj.put("nonceStr",nonceStr);
                returnObj.put("package","prepay_id="+result.get("prepay_id"));
                returnObj.put("signType","MD5");
                //paySign签名算法
                HashMap<String,Object> paySignMap = new HashMap<String,Object>();
                paySignMap.put("appId",params.appid);
                paySignMap.put("nonceStr",nonceStr);
                paySignMap.put("package","prepay_id="+prepayId);
                paySignMap.put("signType","MD5");
                paySignMap.put("timeStamp",Usual.f_getString(time));
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
     * @param iAfterSuccess（自定义结果处理方法）
     * @return
     */
    public static void wxPaymentResult(HttpServletResponse response, HttpServletRequest request, IPayAfterSuccess iAfterSuccess){
        String xmlResult = WeChatUtil.getWeChatResponse(request);
        //System.out.println(xmlResult);
        HashMap<String,Object> result = ConvertUtil.readStringXmlOut(xmlResult);
        //支付结果通知数据处理
        StringBuilder confirmResult = new StringBuilder();
        try{
            if(iAfterSuccess !=null){
                iAfterSuccess.afterPaySuccess(result);
            }
            WeChatUtil.returnParams(response,"SUCCESS","OK");
        }catch (Exception e){
            logger.error(e.getMessage());
            WeChatUtil.returnParams(response,"FAIL","订单支付处理异常");
        }
    }

    /**
     * 退款申请方法
     * @param params
     * @return
     */
    public static ResultVo wechatRefund(WeChatRefundParmas params){
        //加密获取sign
        HashMap<String,Object> signMap = new HashMap<String,Object>();
        signMap.put("appid",params.appid);
        signMap.put("mch_id",params.mch_id);
        signMap.put("nonce_str",params.nonce_str);
        signMap.put("out_trade_no",params.out_trade_no);
        signMap.put("out_refund_no",params.out_refund_no);
        signMap.put("total_fee",params.total_fee+"");
        signMap.put("refund_fee",params.refund_fee+"");
        signMap.put("refund_desc",params.refund_desc);
        signMap.put("notify_url",params.notify_url);
        String sign = WeChatUtil.getSign(signMap,params.key);
        //请求参数
        WeChatRefundRequest entity = new WeChatRefundRequest();
        entity.appid=params.appid;
        entity.mch_id=params.mch_id;
        entity.nonce_str=params.nonce_str;
        entity.out_trade_no=params.out_trade_no;
        entity.out_refund_no=params.out_refund_no;
        entity.total_fee=params.total_fee;
        entity.refund_fee=params.refund_fee;
        entity.refund_desc = params.refund_desc;
        entity.notify_url=params.notify_url;
        entity.sign = sign;
        String xmlStr = ConvertUtil.objectToXml(entity);
        String responseStr = HttpUtil.httpResFulReturnString(Content.REFUND,"POST","text/xml;charset=utf-8","text/xml;charset=utf-8",xmlStr,true);
        logger.info("退款放回值");
        logger.info(responseStr);
        try {
            responseStr = new String(responseStr.getBytes("ISO-8859-1"),"utf-8");
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage());
            return  ResultVoUtil.fail(9999,"退款失败："+e.getMessage());
        }
        // System.out.println(responseStr);
        HashMap<String,Object> result = ConvertUtil.readStringXmlOut(responseStr);

        if(Content.SUCCESS.equals(result.get("return_code"))){
            if (Content.OK.equals(result.get("return_msg"))){
                //退款成功
                return ResultVoUtil.success();
            }else{
                logger.info(JSON.toJSONString(result));
                return ResultVoUtil.fail(RefundExceptionEmnu.REFUND_FAILD);
            }
        }else {
            //说明是系统级别的参数错误（商户id不对之类）
            logger.info(JSON.toJSONString(result));
            return  ResultVoUtil.fail(RefundExceptionEmnu.REFUND_SYSTEM_ERROR);
        }
    }

    /**
     * 退款成功后回调方法
     * @param response
     * @param request
     * @param iAfterSuccess（自定义结果处理方法）
     * @return
     */
    public static void wxRefundResult(HttpServletResponse response, HttpServletRequest request, IRefundAfterSuccess iAfterSuccess){
        String xmlResult = WeChatUtil.getWeChatResponse(request);
        //System.out.println(xmlResult);
        HashMap<String,Object> result = ConvertUtil.readStringXmlOut(xmlResult);
        //支付结果通知数据处理
        StringBuilder confirmResult = new StringBuilder();
        try{
            if(iAfterSuccess !=null){
                iAfterSuccess.afterRefundSuccess(result);
            }
            WeChatUtil.returnParams(response,"SUCCESS","OK");
        }catch (Exception e){
            logger.error(e.getMessage());
            WeChatUtil.returnParams(response,"FAIL","退款处理异常");
        }
    }

    /**
     * 微信退款成功后 加密内容解密
     * @param result
     * @return
     */
    public static HashMap<String,Object> refundDeciphering(HashMap<String,Object> result){
        String xmlResult=WeChatUtil.decodeRefund(result.get("req_info").toString(),"微信Secret");
        HashMap<String,Object> zmlresult = ConvertUtil.readStringXmlOut(xmlResult);
        return zmlresult;
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
    public static void wxPaymentResultTest(HttpServletResponse response, HttpServletRequest request, IPayAfterSuccess iAfterSuccess){
        //自定义处理方法
        if(iAfterSuccess !=null){
            HashMap<String, Object> test = new HashMap<String, Object>();
            test.put("test","afterPaySuccess");
            iAfterSuccess.afterPaySuccess(test);
        }
    }
}
