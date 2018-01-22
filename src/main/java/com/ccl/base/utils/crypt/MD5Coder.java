package com.ccl.base.utils.crypt;

import java.nio.ByteBuffer;
import java.security.MessageDigest;

import org.apache.commons.lang.StringUtils;
/**
 * md5加密类
 * @ClassName Md5Coder
 * @Description TODO
 * @author 向阳
 * @Date 2017年1月11日 上午10:45:17
 * @version 1.0.0
 */
public class MD5Coder {
	
	public static final String KEY_MD5 = "MD5";
	
	public enum Len{
		_32,_16
	}
	
	public static byte[] baseEncryptMD5(byte[] s) throws Exception {
		return baseEncryptMD5(s,null);
	}
	
	/**
	 * MD5加密
	 * 
	 * @param data
	 * @param salt
	 * @return string
	 * @throws Exception
	 */
	public static byte[] baseEncryptMD5(byte[] s,byte[] salt) throws Exception {
		byte[] t = null;
		if(salt==null||salt.length==0){
			t = s;
		}else{
			ByteBuffer bf = ByteBuffer.allocate(s.length + salt.length);
			bf.put(s);
			bf.put(salt);
			t = bf.array();
		}		
		MessageDigest md5 = MessageDigest.getInstance(KEY_MD5);
		md5.update(t);
		byte b[] = md5.digest();
		return b;
	}
	
	/**
	 * MD5加密
	 * 
	 * @param data
	 * @param salt
	 * @return string
	 * @throws Exception
	 */
	public static String baseEncryptMD5(byte[] s,byte[] salt,Len l) throws Exception {
		byte[] r = baseEncryptMD5(s,salt);
		int i;
		StringBuffer buf = new StringBuffer("");
		for (int offset = 0; offset < r.length; offset++) {
			i = r[offset];
			if (i < 0) {
				i += 256;
			}
			if (i < 16) {
				buf.append("0");
			}
			buf.append(Integer.toHexString(i));
		}
		switch (l) {
		case _16:
			return buf.toString().substring(8, 24);
		default:
			return buf.toString();
		}
	}
	
	public static String encryptMD5(String s) throws Exception {
		return encryptMD5(s,null,CryptUtils.DEFAULT_CHARSET,Len._32);
	}
	
	public static String encryptMD5WithSalt(String s,String salt) throws Exception {
		return encryptMD5(s,salt,CryptUtils.DEFAULT_CHARSET,Len._32);
	}
	
	public static String encryptMD5(String s,String charset) throws Exception {
		return encryptMD5(s,null,charset,Len._32);
	}
	
	public static String encryptMD5(String s,Len l) throws Exception {
		return encryptMD5(s,null,CryptUtils.DEFAULT_CHARSET,l);
	}
	
	public static String encryptMD5(String s,String charset,Len l) throws Exception {
		return encryptMD5(s,null,charset,l);
	}
	
	public static String encryptMD5(String s,String salt,String charset) throws Exception {
		return encryptMD5(s,salt,charset,Len._32);
	}
	
	public static String encryptMD5(String s,Len l,String salt) throws Exception {
		return encryptMD5(s,salt,CryptUtils.DEFAULT_CHARSET,l);
	}
	
	public static String encryptMD5(String s,String salt,String charset,Len l) throws Exception {
		if(StringUtils.isEmpty(charset)){
			charset = CryptUtils.DEFAULT_CHARSET;
		}
		byte[] t = s.getBytes(charset);
		byte[] st = null;
		if(!StringUtils.isEmpty(salt)){
			st = salt.getBytes(charset);
		}
		if(l==null){
			l = Len._32;
		}
		return baseEncryptMD5(t,st,l);
	}
	
	
	public static void main(String[] args) throws Exception {
		String x = "我是中国人";
		//System.out.println(encryptMD5(x));
		//System.out.println(encryptMD5(x,null,"utf-8",Len._16));
	}
}
