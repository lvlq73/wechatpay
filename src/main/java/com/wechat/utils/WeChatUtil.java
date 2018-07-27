package com.wechat.utils;

import org.apache.commons.codec.digest.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.util.*;

public class WeChatUtil {
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
    public static String getSign(HashMap<String, String> map,String wechatKey) {

        String result = "";
        try {
            List<HashMap.Entry<String, String>> infoIds = new ArrayList<HashMap.Entry<String, String>>(map.entrySet());
            // 对所有传入参数按照字段名的 ASCII 码从小到大排序（字典序）
            Collections.sort(infoIds, new Comparator<Map.Entry<String, String>>() {

                public int compare(HashMap.Entry<String, String> o1, HashMap.Entry<String, String> o2) {
                    return (o1.getKey()).toString().compareTo(o2.getKey());
                }
            });

            // 构造签名键值对的格式
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, String> item : infoIds) {
                if (item.getKey() != null || item.getKey() != "") {
                    String key = item.getKey();
                    String val = item.getValue();
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
}
