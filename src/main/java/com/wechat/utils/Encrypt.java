package com.wechat.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Security;

public class Encrypt {
    public static boolean initialized = false;

    public static final String ALGORITHM = "AES/ECB/PKCS7Padding";

    /**
     * @param  String str  要被加密的字符串
     * @param  byte[] key  加/解密要用的长度为32的字节数组（256位）密钥
     * @return byte[]  加密后的字节数组
     */
    public static byte[] Aes256Encode(String str, byte[] key){
        initialize();
        byte[] result = null;
        try{
            Cipher cipher = Cipher.getInstance(ALGORITHM, "BC");
            SecretKeySpec keySpec = new SecretKeySpec(key, "AES"); //生成加密解密需要的Key
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            result = cipher.doFinal(str.getBytes("UTF-8"));
        }catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }

    /**
     * @param  byte[] bytes  要被解密的字节数组
     * @param  byte[] key    加/解密要用的长度为32的字节数组（256位）密钥
     * @return String  解密后的字符串
     */
    public static String Aes256Decode(byte[] bytes, byte[] key){
        initialize();
        String result = null;
        try{
            Cipher cipher = Cipher.getInstance(ALGORITHM, "BC");
            SecretKeySpec keySpec = new SecretKeySpec(key, "AES"); //生成加密解密需要的Key
            cipher.init(Cipher.DECRYPT_MODE, keySpec);
            byte[] decoded = cipher.doFinal(bytes);
            result = new String(decoded, "UTF-8");
        }catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }

    public static void initialize(){
        if (initialized) return;
        //Security.addProvider(new BouncyCastleProvider());
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        initialized = true;
    }
    public static void main(String[] args) throws Exception {
        String data="4xjPvn1/cDl2DPjPA6F/poA/GAVyBSGZSMBUCFc0i+O0xxR348d6jSfQutyyzA9eZnnXzbHxqZP89spjOmJqFvxznL9VCeWZ2m+HQ4VbhOYtGjj1k8uuj0bVqiPzB+KFkvtDxpwzoVGK68D15vYvx90BTpidrB4bwyRh8Hv9RzfGzyJm6FLV4JuSmh7T7zW6N8B5TYzGvnRkvE1K67P0OuCkMq/iBYaSf3Ifge27QZ1Hy4IMqGM5gLCwOI2ApVuCA/K5nUIYxz6Ea288rKgndZCaCE4EWX1XwwI0BBGTF8GmBDA8U9f+Mo37ZmJkvfy4Yn2EuC5ZY3Vn1HaoyhomNKBA0dI+CZ59AEj7JAKPYg2X+4FeLSSeFYi6UK5Om7tjf0Gkjq7Bqa2m/hlanobKJrz4X431Pv/IVLcFXVdOdubhpdZevTfEWLeLhmskgvtcm/zrq3QVH+EJFn6eNbLZ9bIr6krg6PFkxQeqQhGNgkPcO65WKS33Yf9JSshroFBb29b2kn6VM3b7zd3bEXJcY4VP1niqwBenNseAzmredbSaAs9hAK43OcLyQNdLtWU7P1Cxmm7MA1eRzJtC9+80c4un3tJq/xhed6sPDg8dNJAvA8pedI7aq2cLlrD0fnzP3MRwABceISB1FMB/BgevCUWFJVhxdm/zpvYpruaMAM3UcnNtwF7FpF0tkPv2HhUyshhbSJtgbrKZa4b9M22GekCaTmG6Ardc4BCWHB3VHM5B7uqfInySG2f5WtkT31eUWUwsl/KgTkwapAKp8OTkJhnRu6tNxq+uZSE8zA0hovhoQ3snPz1Di+iVAxFo5wlnqe3qmUlBKWyUI8XI3dkfvfGL+UFPFNeVXPc5JERRBKALlkSX+DePEv94utD9TKlQ4tVFxJ9q0wD6nsmaTde8wzbSLXG8CAvTVpfrtXrHa3Bzjf1bNSksTMQ/M3zIjLsewpFiypm3O/WEt2areffHNkydo8YoRBEbD47PDi2ZEqazmAyieEo8JsGThg6myXVE/nohMNUVMeON3xQ4kx847/B4KXf8DqIPYTzDQlCfvNcTvs94CP4usWEJezyBeOtcCkMDsxeuwOYKTn7E730oDw==";
        String key="dumqu788eg0ommtjl8uwzu5cveb8i1x3";
        byte[] ss= Base64Utils.decode(data);
          String md=MD5.getMD5(key);
          String cdsdsfsdf=  Encrypt.Aes256Decode(ss,md.getBytes());
          System.out.println(cdsdsfsdf);
//        Encrypt.Aes256Decode()
    }
}
