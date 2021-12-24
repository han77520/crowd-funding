package com.whw.crowd.util;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

/**
 * @author 王瀚文
 * @Description: 通用工具类
 * @date 2021-12-19 13:55
 */
public class CrowdUtil {

    /**
     * 判断当前请求是否为Ajax请求。
     *
     * @param request
     * @return 返回true代表是Ajax，返回false代表不是Ajax请求
     */
    public static boolean isAjaxRequestType(HttpServletRequest request) {
        String acceptHeader = request.getHeader("Accept");
        String XRequestHeader = request.getHeader("X-Requested-With");

        return acceptHeader != null && acceptHeader.contains("application/json")
                || (XRequestHeader != null && XRequestHeader.contains("XMLHttpRequest"));
    }


    /**
     * 对明文字符串进行MD5加密
     *
     * @param source
     * @return
     */
    public static String md5(String source) {

        // 1.判断source是否有效
        if (source == null || "".equals(source)) {

            // 如果不是有效字符串就抛出异常
            throw new RuntimeException(CrowdConstant.MESSAGE_STRING_INVALIDATE);
        }

        try {

            // 创建 MessageDigest 对象
            String algorithm = "MD5";
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);

            // 获取明文字符串的字节数组
            byte[] bytes = source.getBytes();

            // 进行加密
            byte[] digest = messageDigest.digest(bytes);

            // 创建 BigInteger 对象，
            BigInteger bigInteger = new BigInteger(1, digest);

            // 将加密后的byte数组按16进制进行转换，最后在变成字符串
            String encoded = bigInteger.toString(16).toUpperCase();

            return encoded;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


}
