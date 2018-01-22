package com.ccl.base.utils.qiniu;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.gson.JsonObject;
import com.qiniu.util.UrlSafeBase64;

public class QiniuUploadTokenBuilder {
    private static final Log LOG = LogFactory.getLog(QiniuUploadTokenBuilder.class);
    public static String buildUploadToken(){
        //七牛上传策略
        JsonObject uploadPolicy = new JsonObject();
        uploadPolicy.addProperty("scope",QiniuConfig.BUCKET);
        uploadPolicy.addProperty("deadline",1891491200);
        uploadPolicy.addProperty("insertOnly", 1);
//        uploadPolicy.addProperty("returnBody", "{'" +
//                "name':$(fname)" +
//                "'size':$(fsize)" +
//                "'w':$(imageInfo.width)" +
//                "'h':$(imageInfo.height)" +
//                "'hash':$(etag)" +
//                "}");

        //对JSON编码的上传策略进行URL安全的Base64编码
        String encodePutPolicy = UrlSafeBase64.encodeToString(uploadPolicy.toString());

        //使用SecretKey对上一步生成的待签名字符串计算HMAC-SHA1签名
        byte[] sign = null;
        try {
            sign = HMACSHA1Helper.HmacSHA1Encrypt(encodePutPolicy,QiniuConfig.SECRET_KEY);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //对签名进行URL安全的Base64编码
        String encodeSign = UrlSafeBase64.encodeToString(sign);

        //将AccessKey、encodedSign和encodedPutPolicy用:连接起来
        String uploadToken = QiniuConfig.ACCESS_KEY+":"+encodeSign+":"+encodePutPolicy;
        LOG.debug("上传token："+uploadToken);

        return uploadToken;
    }

    /**
     * 自定义返回格式
     * @return
     */
    public static String buildUploadToken1(){
        //七牛上传策略
        JsonObject uploadPolicy = new JsonObject();
        uploadPolicy.addProperty("scope",QiniuConfig.BUCKET);
        uploadPolicy.addProperty("deadline",1891491200);
        uploadPolicy.addProperty("returnBody", "{'error':0, 'url' : $(etag)  , 'name' : $(fname) }");
        uploadPolicy.addProperty("returnURL",QiniuConfig.RETURN_URL+"/layout/fileManager/returnUrl");
        //对JSON编码的上传策略进行URL安全的Base64编码
        String encodePutPolicy = UrlSafeBase64.encodeToString(uploadPolicy.toString());
        LOG.info("回调URL========="+QiniuConfig.RETURN_URL+"/layout/fileManager/returnUrl");
        //使用SecretKey对上一步生成的待签名字符串计算HMAC-SHA1签名
        byte[] sign = null;
        try {
            sign = HMACSHA1Helper.HmacSHA1Encrypt(encodePutPolicy,QiniuConfig.SECRET_KEY);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //对签名进行URL安全的Base64编码
        String encodeSign = UrlSafeBase64.encodeToString(sign);

        //将AccessKey、encodedSign和encodedPutPolicy用:连接起来
        String uploadToken = QiniuConfig.ACCESS_KEY+":"+encodeSign+":"+encodePutPolicy;

        LOG.debug("上传token："+uploadToken);

        return uploadToken;
    }

    /**
     * 获取文件名token
     * @return
     */
    public static String buildUploadToken2(){
        //七牛上传策略
        JsonObject uploadPolicy = new JsonObject();
        uploadPolicy.addProperty("scope",QiniuConfig.BUCKET);
        uploadPolicy.addProperty("deadline",1891491200);
        //对JSON编码的上传策略进行URL安全的Base64编码
        String encodePutPolicy = UrlSafeBase64.encodeToString(uploadPolicy.toString());
        //使用SecretKey对上一步生成的待签名字符串计算HMAC-SHA1签名
        byte[] sign = null;
        try {
            sign = HMACSHA1Helper.HmacSHA1Encrypt(encodePutPolicy,QiniuConfig.SECRET_KEY);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //对签名进行URL安全的Base64编码
        String encodeSign = UrlSafeBase64.encodeToString(sign);

        //将AccessKey、encodedSign和encodedPutPolicy用:连接起来
        String uploadToken = QiniuConfig.ACCESS_KEY+":"+encodeSign+":"+encodePutPolicy;

        LOG.debug("上传token："+uploadToken);

        return uploadToken;
    }
}
