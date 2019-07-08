package com.wechat.utils;

import com.alibaba.fastjson.JSONObject;
import com.wechat.config.Content;
import com.wechat.entity.ReturnParams;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.util.*;

public class WeChatUtil {
    private static Logger logger = LoggerFactory.getLogger(WeChatUtil.class);
    /**
     * 数据签名加密
     * @param str
     * @return
     */
    public static String getSha1(String str){
        if(str == null || str.length()==0){
            return null;
        }
        char hexDigits[]={'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};

        try {
            MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
            mdTemp.update(str.getBytes("UTF-8"));

            byte[] md = mdTemp.digest();
            int j = md.length;
            char buf[] = new char[j*2];
            int k = 0;
            for(int i=0;i<j;i++){
                byte byte0 = md[i];
                buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
                buf[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(buf);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 生成签名
     * @param map
     * @param wechatKey 密钥 key
     * @return
     */
    public static String getSign(Map<String, Object> map, String wechatKey) {

        String result = "";
        try {
            List<Map.Entry<String, Object>> infoIds = new ArrayList<Map.Entry<String, Object>>(map.entrySet());
            // 对所有传入参数按照字段名的 ASCII 码从小到大排序（字典序）
            Collections.sort(infoIds, new Comparator<Map.Entry<String, Object>>() {
                public int compare(Map.Entry<String, Object> o1, Map.Entry<String, Object> o2) {
                    return (o1.getKey()).toString().compareTo(o2.getKey());
                }
            });

            // 构造签名键值对的格式
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, Object> item : infoIds) {
                if (item.getKey() != null || item.getKey() != "") {
                    String key = item.getKey();
                    String val = Usual.f_getString(item.getValue());
                    if (!(val == "" || val == null)) {
                        sb.append( key + "=" + val +"&");
                    }
                }

            }
//          sb.append(PropertyManager.getProperty("SIGNKEY"));
            sb.append("key="+wechatKey);
            result = sb.toString();

            //进行MD5加密
            result = DigestUtils.md5Hex(result).toUpperCase();
        } catch (Exception e) {
            return null;
        }
        return result;
    }
    /**
     * 处理xml请求信息
     */
    public static String getWeChatResponse(HttpServletRequest request) {
        BufferedReader bis = null;
        String result = "";
        try {
            bis = new BufferedReader(new InputStreamReader(request.getInputStream()));
            String line = null;
            while ((line = bis.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    /**
     * 生成随机码
     * @return
     */
    public static String getRandomString(int length) { //length表示生成字符串的长度
        String base = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }
    /**
     * 生成随机数
     * @return
     */
    public static String getRandom(int length){
        Random random = new Random();
        String result="";
        for (int i=0;i<length;i++)
        {
            result+=random.nextInt(10);
        }
        return result;
    }

    /**
     * 商户退款解密
     * @param data 要解密的数据
     * @param wecatSecret Secret 商户密钥
     * @return 解密后的内容
     */
    public static String decodeRefund(String data, String wecatSecret){
        try {
            byte[] ss= Base64Utils.decode(data);
            String md=MD5.getMD5(wecatSecret);
            return  Encrypt.Aes256Decode(ss,md.getBytes());
        }catch (Exception e){
            logger.error(e.getMessage());
            return  null;
        }
    }

    public static boolean checkIsSignValid(Map<String, Object> resMap){
        //Map<String,String> wechatParams = ServiceProConfig.getWechatParams();
        String key  = Content.key;//wechatParams.get("key");
        Map<String, Object> copyMap = new HashMap<String, Object>();
        copyMap.putAll(resMap);
        String sign = Usual.f_getString(copyMap.get("sign"));
        copyMap.remove("sign");
        String validSign = getSign(copyMap,key);
        if(sign.equals(validSign)){
            return true;
        }
        return false;
    }

    /**
     * 返回给微信的值
     * @param response
     * @param code
     * @param msg
     */
    public static void returnParams(HttpServletResponse response, String code, String msg){
        try{
            ReturnParams res = new ReturnParams(code,msg);
            String xml = ConvertUtil.objectToXml(res);
            if(!StringUtil.isEmpty(xml)){
                response.setCharacterEncoding("utf-8");
                PrintWriter writer = response.getWriter();
                writer.write(xml);
                writer.close();
            }
        }catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public static void main(String[] args) throws Exception {
        String data="2tPmfGszMXcAijzmhvJcV5Sq44qc+shghWE5L21QkNCHB4zZg0c+EptWDCMZxHNorV5HNVXfu+6NlTmAL3nTyx1mdMVyjjFsfdXyyIs1qPg4wabObiGLpib/5LoKMjY+MntAfh7kU4oDN5pdw04WjVyInQLBKIJMQ1LcRRN93fY2XiTIfaeYmq73rVmhxqt1QXtnctqAWF3tJandrbVPM/XMr9BOTZRcks+x3URmOjPCHwY5ZGm/DC9B5YwHNfj47sIzOhfMLL1iGd0G6YynE4azwAeE1VhBiKNWsK3SepkkET6ndNyJp03lxFd5kssOwRgondjbpAPySfrWAQqA7vgzP+fpNJrma+cgins97V+zYa3bFNp2t4Onj5LnS+UKJsJkjL9XwxQ4PhVgCL5lyh7CQMIhZGe8tLWyCm5gDxlUpexxvj2yFhqVwZ4pNtEC6BJAtyA0k1dzXiSq0unv+xvEGwtdCfooKSCTz4KdT/fK2hAWkW4w0E72DIysc2UtFsSEkMpNEKz5r3wNe2qLCrqCNTe8PizGYFPouKdn2KOmlGbinS7zM+5kfvK2Og2NSrRDP8wgB7xBR1xMDuYY+XlX2L0B/Vw9RDrzf8ep8GlrwTHFgZUcOGLWtHiS/GGrXYg7eLOiw11IBauhcARrObG2GnK1UsdeqBDn1ynXz+fdkTX2TH1iITG0doaxxGT8apwgVR7tPbfwVzbUWmk2wDjY/LTIpUL1ivaOlWYG5Jv+UVXq7L7qd+gZsFt+Is31WESQnE0s1EufRRHwmLX07NaqnBjC8cP3CL0zmdglwZkfsEJ+gRf3KwOI0CfWe20kBE+L/vm1K4xSqZfqSrgm/Kz5AhvWc7QdeoECFZ2boOUlrXYBilLwsrhKJSJRULAII7Ba6lZBzdZz63MNpzQkqS7bxIU4v8PRgu0F/lemk+Y6tpHnANitzYdLl3MSTiBnIiO/+TUzmVa3dzbGu+/WAh1OXKfTEt/BfXWKIfMvIdYNRh0zquTjWzKSJG84EAnY/PIi0lve//RL2qhThAeZzlp/gH3wdQHyO1Y9xUW12Qo=";
        String key="QF67HoONU60TsDHN2Mya0rpcnTsdwf76";
        //System.out.println(WeChatUtil.decodeRefund(data,key));
        System.out.println(JSONObject.toJSONString(ConvertUtil.readStringXmlOut(WeChatUtil.decodeRefund(data,key))));
    }
}
