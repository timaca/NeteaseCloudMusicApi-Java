package com.roomaja.neteasecloudmusicapi.util;

import com.alibaba.fastjson.JSONObject;
import org.springframework.util.DigestUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;
import java.util.Base64;

public class CryptoUtil {

    private static String nonce = "0CoJUm6Qyw8W8jud";
    private static String pubKey = "010001";
    private static String modulus = "00e0b509f6259df8642dbc35662901477df22677ec152b5ff68ace615bb7b725152b3ab17a876aea8a5aa76d2e417629ec4ee341f56135fccf695280104e0312ecbda92557c93870114af6c9d05c4f7f0c3685b7a46bee255932575cce10b424d813cfe4875d3e82047b97ddef52741d546b8e289dc6935b3ece0462db0a22b8e7";

    /**
     * 产生16位的随机字符串
     *
     * @param size
     * @return
     */
    private static String createSecretKey(int size) {
        String keys = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        String key = "";
        for (int i = 0; i < size; i++) {
            Double index = Math.floor(Math.random() * keys.length());
            key += keys.charAt(index.intValue());
        }
        return key;
    }

    /**
     * aes加密
     *
     * @param content
     * @param key
     * @return
     */
    private static String aesEncrypt(String content, String key) {

        String result = null;
        if (content == null || key == null) return result;

        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key.getBytes("utf-8"), "AES"), new IvParameterSpec("0102030405060708".getBytes("utf-8")));
            byte[] bytes = cipher.doFinal(content.getBytes("utf-8"));

            result = Base64.getEncoder().encodeToString(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;

    }

    /**
     * 长度不够前面补充0
     *
     * @param str
     * @param size
     * @return
     */
    private static String zFill(String str, int size) {
        while (str.length() < size) {
            str = "0" + str;
        }
        return str;
    }

    /**
     * rsa加密
     *
     * @param text
     * @param pubKey
     * @param modulus
     * @return
     */
    private static String rsaEncrypt(String text, String pubKey, String modulus) {

        //反转字符串
        text = new StringBuffer(text).reverse().toString();

        BigInteger biText = new BigInteger(strToHex(text), 16);
        BigInteger biEx = new BigInteger(pubKey, 16);
        BigInteger biMod = new BigInteger(modulus, 16);
        BigInteger biRet = biText.modPow(biEx, biMod);

        return zFill(biRet.toString(16), 256);

    }

    /**
     * 字符串转成16进制字符串
     *
     * @param s
     * @return
     */
    private static String strToHex(String s) {
        String str = "";
        for (int i = 0; i < s.length(); i++) {
            int ch = (int) s.charAt(i);
            String s4 = Integer.toHexString(ch);
            str = str + s4;
        }
        return str;
    }

    /**
     * 加密方法
     *
     * @param content
     * @return
     */
    public static String[] Encrypt(String content) {
        String[] result = new String[2];
        String key = createSecretKey(16);

        String encText = aesEncrypt(aesEncrypt(content, nonce), key);
        String encSecKey = rsaEncrypt(key, pubKey, modulus);
        result[0] = encText;
        result[1] = encSecKey;
        return result;

    }

    /**
     * MD5加密
     *
     * @param content
     * @return
     */
    public static String getMd5(String content) {
        String result = null;
        try {
            result = DigestUtils.md5DigestAsHex(content.getBytes("utf-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    public static void main(String[] args) {
        String content = "{\"ids\":\"[484730184]\",\"br\":128000,\"csrf_token\":\"\"}";

        String[] result = Encrypt(content);

        System.out.println("encText = " + result[0] + " ,encSecKey = " + result[1]);

        System.out.println(" = " + getMd5("abc"));

        JSONObject object = new JSONObject();
        object.put("username", "dd");
        object.put("password", "cc");
        object.put("rememberLogin", "bb");
        object.put("clientToken", "aa");

        System.out.println("object = " + object.toJSONString());
        System.out.println("object = " + object.toString());

//        a9d6e8ec07b535a8945415b3786244b5
        System.out.println(CryptoUtil.getMd5("ddm000000"));


    }
}
