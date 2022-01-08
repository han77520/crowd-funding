package com.whw.crowd.util;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.comm.ResponseMessage;
import com.aliyun.oss.model.PutObjectResult;

import javax.servlet.http.HttpServletRequest;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @author 王瀚文
 * @Description: 通用工具类
 * @date 2021-12-19 13:55
 */
public class CrowdUtil {


    public static ResultEntity<String> uploadFileToOss(String endpoint, String accessKeyId, String accessKeySecret,
                                                       InputStream inputStream,
                                                       String bucketName,
                                                       String bucketDomain,
                                                       String originalName) {
        // 创建 OSSClient 实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        String folderName = new SimpleDateFormat("yyyyMMdd").format(new Date());

        String fileMainName = UUID.randomUUID().toString().replace("-", "");

        String extensionName = originalName.substring(originalName.lastIndexOf("."));

        String objectName = folderName + "/" + fileMainName + extensionName;

        PutObjectResult putObjectResult = ossClient.putObject(bucketName, objectName, inputStream);

        try {
            ResponseMessage responseMessage = putObjectResult.getResponse();

            if (responseMessage == null) {
                String ossFileAccessPath = bucketDomain + "/" + objectName;

                return ResultEntity.successWithData(ossFileAccessPath);
            } else {
                // 获取响应状态码
                int statusCode = responseMessage.getStatusCode();

                // 如果请求没有成功，获取错误消息
                String errorMessage = responseMessage.getErrorResponseAsString();

                return ResultEntity.failed(" 当 前 响 应 状 态 码 =" + statusCode + " 错 误 消 息 =" + errorMessage);
            }
        } catch (Exception e) {
            e.printStackTrace();

            return ResultEntity.failed(e.getMessage());
        } finally {
            if(ossClient != null) {
                // 关闭 OSSClient。
                ossClient.shutdown();
            }
        }
    }


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
