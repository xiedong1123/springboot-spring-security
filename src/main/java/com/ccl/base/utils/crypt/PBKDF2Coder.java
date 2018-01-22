package com.ccl.base.utils.crypt;

import java.security.spec.KeySpec;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import org.apache.commons.lang.StringUtils;

/**
 * PBKDF2安全编码组件,安全性比较高，推荐作为重要信息的加密方式，如登录口令等
 * 
 * @version 1.0
 * @since 1.0
 */
public  class PBKDF2Coder {

	 public static final String PBKDF2_ALGORITHM = "PBKDF2WithHmacSHA1";  
	 
	 public enum Type{
			HEX,BASE64
		}
	 
	 /** 
     * 生成密文的长度 
     */  
	 public static final int HASH_BIT_SIZE = 128 * 4;  
	  
     /** 
     * 迭代次数 
     */  
	 public static final int PBKDF2_ITERATIONS = 1000;  
	 
	 
	 public static byte[] baseEncryptPBKDF2(char[] s,byte[] salt,String encrypt) throws Exception {
		 	KeySpec spec = new PBEKeySpec(s, salt, PBKDF2_ITERATIONS, HASH_BIT_SIZE);  
		 	SecretKeyFactory f = SecretKeyFactory.getInstance(encrypt);
		 	SecretKey key = f.generateSecret(spec);
			return key.getEncoded();
	}
	 
	 public static byte[] baseEncryptPBKDF2(char[] s,byte[] salt) throws Exception {
			return baseEncryptPBKDF2(s,salt,PBKDF2_ALGORITHM);
	}
	 
	 
	 public static String baseEncryptPBKDF2(char[] s,byte[] key,String encrypt,Type t)
				throws Exception {
			switch (t) {
			case BASE64:
				return BASE64Coder.encode((baseEncryptPBKDF2(s,key,encrypt)));
			default:
				return CryptUtils.toHex(baseEncryptPBKDF2(s,key,encrypt));
			}
		}
	 
	 public static String encryptPBKDF2(String s,byte[] salt,String encrypt,Type type)
				throws Exception {
			if(StringUtils.isEmpty(encrypt)){
				encrypt = PBKDF2_ALGORITHM;
			}
			if(type==null){
				type = Type.HEX;
			}
			return baseEncryptPBKDF2(s.toCharArray(),salt,encrypt,type);
		}
	 
	 public static String encryptPBKDF2(String s,byte[] salt,String encrypt)
				throws Exception {
			
			return encryptPBKDF2(s,salt,encrypt,Type.HEX);
		}
	 
	 public static String encryptPBKDF2(String s,byte[] salt,Type type)
				throws Exception {
			return encryptPBKDF2(s,salt,PBKDF2_ALGORITHM,type);
	 }
	 
	 public static String encryptPBKDF2(String s,byte[] salt)
				throws Exception {
			return encryptPBKDF2(s,salt,PBKDF2_ALGORITHM,Type.HEX);
	 }
}

