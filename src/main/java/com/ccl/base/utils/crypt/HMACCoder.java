package com.ccl.base.utils.crypt;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang.StringUtils;

public class HMACCoder {
	
	public enum Type{
		HEX,BASE64
	}
	
	public static final String KEY_HMAC = "HmacMD5";
	
	/**
	 * 初始化HMAC密钥:HmacMD5 HmacSHA1 HmacSHA256 HmacSHA384 HmacSHA512
	 * 必须随机生成，固定的密钥安全性很低 
	 * @Description 
	 * @param encrypt
	 * @return 返回base64的字符串
	 * @throws Exception
	 */
	public static String initMacKeys(String encrypt) throws Exception {
		KeyGenerator keyGenerator = KeyGenerator.getInstance(encrypt);
		SecretKey secretKey = keyGenerator.generateKey();
		return BASE64Coder.encode((secretKey.getEncoded()));
	}
	
	/**
	 * 
	 * @Description TODO
	 * @param encrypt
	 * @return 返回字节数组
	 * @throws Exception
	 */
	public static byte[] initMacKeyb(String encrypt) throws Exception {
		KeyGenerator keyGenerator = KeyGenerator.getInstance(encrypt);
		SecretKey secretKey = keyGenerator.generateKey();
		return secretKey.getEncoded();
	}
	
	public static byte[] baseEncryptHMAC(byte[] s,byte[] key)
			throws Exception {
		return baseEncryptHMAC(s,key,KEY_HMAC);
	}
	
	public static byte[] baseEncryptHMAC(byte[] s,byte[] key,String encrypt) throws Exception {
		SecretKey secretKey = new SecretKeySpec(key, encrypt);
		Mac mac = Mac.getInstance(secretKey.getAlgorithm());
		mac.init(secretKey);
		byte b[] = mac.doFinal(s);
		return b;
	}
	
	public static String baseEncryptHMAC(byte[] s,byte[] key,String encrypt,Type t)
			throws Exception {
		switch (t) {
		case BASE64:
			return BASE64Coder.encode((baseEncryptHMAC(s,key,encrypt)));
		default:
			return CryptUtils.toHex(baseEncryptHMAC(s,key,encrypt));
		}
	}
	
	public static String encryptHMAC(String s,byte[] key,String encrypt,String charset,Type type)
			throws Exception {
		if(StringUtils.isEmpty(charset)){
			charset = CryptUtils.DEFAULT_CHARSET;
		}
		byte[] t = s.getBytes(charset);
		if(StringUtils.isEmpty(encrypt)){
			encrypt = KEY_HMAC;
		}
		if(type==null){
			type = Type.HEX;
		}
		return baseEncryptHMAC(t,key,encrypt,type);
	}
	
	public static String encryptHMAC(String s,byte[] key,String encrypt)
			throws Exception {
		return encryptHMAC(s,key,encrypt,CryptUtils.DEFAULT_CHARSET,Type.HEX);
	}
	
	public static String encryptHMAC(String s,byte[] key,String encrypt,Type type)
			throws Exception {
		return encryptHMAC(s,key,encrypt,CryptUtils.DEFAULT_CHARSET,type);
	}
	
	public static String encryptHMAC(String s,byte[] key,String encrypt,String charset)
			throws Exception {
		return encryptHMAC(s,key,encrypt,charset,Type.HEX);
	}
	
	public static String encryptHMAC(byte[] key,String s,String charset)
			throws Exception {
		return encryptHMAC(s,key,KEY_HMAC,charset,Type.HEX);
	}
	
	public static String encryptHMAC(byte[] key,String s,String charset,Type type)
			throws Exception {
		return encryptHMAC(s,key,KEY_HMAC,charset,type);
	}
}
