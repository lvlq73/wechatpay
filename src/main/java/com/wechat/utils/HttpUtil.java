package com.wechat.utils;

import com.ning.http.client.*;
import org.apache.http.ssl.SSLContexts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import java.security.KeyStore;
import java.util.Map;
import java.util.Set;

/**
 * http请求工具类
 */
public class HttpUtil {
    private static Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    private static final  String defualContentType="application/json;charset=utf-8";
    private static final  String defualAccepType="application/json;charset=utf-8";
    private static AsyncHttpClientConfig.Builder configBuilder = null;

    private static SSLContext wx_ssl_context = null; //微信支付ssl证书

    static{
        Resource resource = new ClassPathResource("证书路径");//获取微信证书 或者直接从文件流读取 填写自己的证书路径
        char[] keyStorePassword ="自己证书密码".toCharArray(); //证书密码 自己填写
        try {
            KeyStore keystore = KeyStore.getInstance("PKCS12");
            keystore.load(resource.getInputStream(), keyStorePassword);
            wx_ssl_context = SSLContexts.custom()
                    .loadKeyMaterial(keystore, keyStorePassword)//这里也是写密码的
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化配置
     */
    private static  AsyncHttpClient getClient(boolean isSSL){
        if(configBuilder == null){
            configBuilder=new AsyncHttpClientConfig.Builder().setHostnameVerifier(new TrustAnyHostnameVerifier());
            configBuilder.setMaxConnections(500);
             /*使用默认值：*/
            configBuilder.setConnectTimeout(3000);
            configBuilder.setReadTimeout(5000);
            configBuilder.setRequestTimeout(6000);
            //configBuilder.setAllowPoolingConnections(true);
        }
        if(isSSL){
            configBuilder.setSSLContext(wx_ssl_context);
            //configBuilder.setSslSessionCacheSize();
            configBuilder.setSslSessionTimeout(6000);
        }
       return new AsyncHttpClient(configBuilder.build());
    }

    /**
     * get请求
     * @param url
     * @return
     */
    public static String httpResFulGET(String url){
        return httpResFulReturnString(url,"GET",defualContentType,defualAccepType,null,false);
    }

    /**
     * put请求
     * @param url
     * @param data
     * @return
     */
    public static String httpResFulPUT(String url,String data){
        return httpResFulReturnString(url,"GET",defualContentType,defualAccepType,data,false);
    }

    /**
     * post请求
     * @param url
     * @param data
     * @return
     */
    public static String httpResFulPost(String url,String data){
        return httpResFulReturnString(url,"POST",defualContentType,defualAccepType,data,false);
    }

    /**
     * delete请求
     * @param url
     * @return
     */
    public static String httpResFulDelete (String url){
        return httpResFulReturnString(url,"DELETE ",defualContentType,defualAccepType,null,false);
    }

    /**
     * 自定义请求方式
     * @param url
     * @param method
     * @param data
     * @return
     */
    public static String httpResFul(String url,String method,String data){
        return httpResFulReturnString(url,method,defualContentType,defualAccepType,data,false);
    }

    /**
     * http请求
     * @param url
     * @param method 请求方式 例如post 或 get
     * @param contentType 请求的数据格式
     * @param accept 接收的数据格式
     * @param data 数据
     * @return
     */
    public static String httpResFulReturnString(String url,String method,String contentType,String accept,Object data,boolean isSSL){
        AsyncHttpClient client =getClient(isSSL);
        String result = null;
        logger.info("url: " + url);
        logger.info("request: " + data);
        try {
                RequestBuilder mRequestBuilder = new RequestBuilder();
                mRequestBuilder.setUrl(url);
                mRequestBuilder.setMethod(method);
                mRequestBuilder.addHeader("Content-Type",StringUtil.isEmpty(contentType)?defualContentType:contentType);
                mRequestBuilder.addHeader("Accept",StringUtil.isEmpty(accept)?defualAccepType:accept);
               if(data!=null){
                   if(data instanceof  Map){
                       Map  map = (Map) data;
                       if(map!=null&&map.size()>0){
                           Set<String> keys = map.keySet();
                           for(String key:keys){
                               mRequestBuilder.addFormParam(key, (String) map.get(key));
                           }
                       }
                   }else if(data instanceof String){
                       mRequestBuilder.setBody((String)data);
                   }else if(data instanceof byte[]){
                       mRequestBuilder.setBody((byte[])data);
                   }
               }
                Request request = mRequestBuilder.build();
                ListenableFuture<Response> rp = client.executeRequest(request,new AsyncCompletionHandler(){
                    public Object onCompleted(Response response) throws Exception {
                        return response;
                    }
                });

                Response response = rp.get();
                result =  response.getResponseBody();
        } catch (Exception e) {
             logger.error(e.getMessage());
        }finally {
            close(client);
        }
        return result;
    }

    /**
     * get请求
     * @param url
     * @return
     */
    public static byte[] httpResFulReturnByteGET(String url){
        return httpResFulReturnByte(url,"GET",defualContentType,defualAccepType,null,false);
    }
    /**
     * put请求
     * @param url
     * @param data
     * @return
     */
    public static byte[] httpResFulReturnBytePUT(String url,String data){
        return httpResFulReturnByte(url,"GET",defualContentType,defualAccepType,data,false);
    }
    /**
     * post请求
     * @param url
     * @param data
     * @return
     */
    public static byte[] httpResFulReturnBytePost(String url,String data){
        return httpResFulReturnByte(url,"POST",defualContentType,defualAccepType,data,false);
    }
    /**
     * delete请求
     * @param url
     * @return
     */
    public static byte[] httpResFulReturnByteDelete (String url){
        return httpResFulReturnByte(url,"DELETE ",defualContentType,defualAccepType,null,false);
    }
    /**
     * 自定义请求方式
     * @param url
     * @param method
     * @param data
     * @return
     */
    public static byte[] httpResFulReturnByte(String url,String method,String data){
        return httpResFulReturnByte(url,method,defualContentType,defualAccepType,data,false);
    }
    /**
     * http请求
     * @param url
     * @param method 请求方式 例如post 或 get
     * @param contentType 请求的数据格式
     * @param accept 接收的数据格式
     * @param data 数据
     * @return
     */
    public static byte[]  httpResFulReturnByte(String url,String method,String contentType,String accept,Object data,boolean isSSL){
        AsyncHttpClient client =getClient(isSSL);
        byte[]  result = null;
        logger.info("url: " + url);
        logger.info("request: " + data);
        try {
            RequestBuilder mRequestBuilder = new RequestBuilder();
            mRequestBuilder.setUrl(url);
            mRequestBuilder.setMethod(method);
            mRequestBuilder.addHeader("Content-Type",StringUtil.isEmpty(contentType)?defualContentType:contentType);
            mRequestBuilder.addHeader("Accept",StringUtil.isEmpty(accept)?defualAccepType:accept);
            if(data!=null){
                if(data instanceof  Map){
                    Map  map = (Map) data;
                    if(map!=null&&map.size()>0){
                        Set<String> keys = map.keySet();
                        for(String key:keys){
                            mRequestBuilder.addFormParam(key, (String) map.get(key));
                        }
                    }
                }else if(data instanceof String){
                    mRequestBuilder.setBody((String)data);
                }else if(data instanceof byte[]){
                    mRequestBuilder.setBody((byte[])data);
                }
            }
            Request request = mRequestBuilder.build();
            ListenableFuture<Response> rp = client.executeRequest(request,new AsyncCompletionHandler(){
                public Object onCompleted(Response response) throws Exception {
                    return response;
                }
            });

            Response response = rp.get();
            result =  response.getResponseBodyAsBytes();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }finally {
            close(client);
        }
        return result;
    }

    /**
     * 关闭方法
     */
    public static void close(AsyncHttpClient client ){
        if (client != null) {
            client.close();
            client = null;
        }
    }

    //post请求方法(测试用)
    @Deprecated
    public  String sendPost(String url, String data) {
        AsyncHttpClient client =getClient(false);
        String result = null;
        logger.info("url: " + url);
        logger.info("request: " + data);
        try {
            try {
                RequestBuilder mRequestBuilder = new RequestBuilder();
                mRequestBuilder.setUrl(url);
                mRequestBuilder.setMethod("GET");
                mRequestBuilder.addHeader("Content-Type","application/json;charset=utf-8");
                mRequestBuilder.addHeader("Accept","text/json");
               // mRequestBuilder.addHeader("Charset","UTF-8");
                mRequestBuilder.setBody(data);
                Request request = mRequestBuilder.build();
                ListenableFuture<Response> rp = client.executeRequest(request,new AsyncCompletionHandler(){
                    public Object onCompleted(Response response) throws Exception {
                        return response;
                    }
                });
                Response response = rp.get();
                result =  response.getResponseBody();
            } finally {
                close(client);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static class TrustAnyHostnameVerifier implements HostnameVerifier {
        public boolean verify(String hostname, SSLSession session) {
            // 直接返回true
            return true;
        }
    }

}
