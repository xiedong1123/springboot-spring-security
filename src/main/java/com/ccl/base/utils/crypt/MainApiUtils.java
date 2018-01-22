/*package com.ccl.base.utils.crypt;

import java.io.UnsupportedEncodingException;

import com.alibaba.fastjson.JSONObject;
import com.changhong.cacsdk.api.MainApi;
import com.changhong.cacsdk.entity.CryptEntity;

public class MainApiUtils {
	
	private static final MainApi api = new MainApi();
	
	//加密用到的key值
	private static final byte[] KEY = null;
	
	
	*//**
	 * 
	 * @Description 返回字符串的加密字符串
	 * @param data
	 * @return
	 * @throws UnsupportedEncodingException
	 *//*
	public static String encryptString(String data) throws UnsupportedEncodingException{
		CryptEntity aesEncrypt = api.aesEncrypt(data.getBytes("UTF-8"), KEY);
		String cryptStr = BASE64Coder.encode(aesEncrypt.getParam());
		return cryptStr;
	}
	
	
	*//**
	 * 
	 * @Description 返回data的json字符串加密字符串
	 * @param data
	 * @return
	 * @throws UnsupportedEncodingException
	 *//*
	public static String encryptJson(Object data) throws UnsupportedEncodingException{
		String  jsonStr = JSONObject.toJSONString(data);
		CryptEntity aesEncrypt = api.aesEncrypt(jsonStr.getBytes("UTF-8"), KEY);
		String cryptStr = BASE64Coder.encode(aesEncrypt.getParam());
		return cryptStr;
	}
	
}
*/