package com.ccl.base.utils.crypt;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
/**
 * 各种加密辅助类
 * @ClassName CryptUtils
 * @Description TODO
 * @author 向阳
 * @Date 2017年1月11日 上午10:30:04
 * @version 1.0.0
 */
public class CryptUtils {
	
	/** 
     * 盐的长度 
     */  
    public static final int SALT_BYTE_SIZE = 32 / 2; 
    
    /**
     * 用于各加密工具的默认的字符编码
     */
    public static final String DEFAULT_CHARSET = "UTF-8";
	
	/** 
     * 十六进制字符串转二进制字符串 
     *  
     * @param   hex         the hex string 
     * @return              the hex string decoded into a byte array       
     */  
    public static byte[] fromHex(String hex) {  
        byte[] binary = new byte[hex.length() / 2];  
        for (int i = 0; i < binary.length; i++) {  
            binary[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);  
        }  
        return binary;  
    }  
  
    /** 
     * 二进制字符串转十六进制字符串 
     *  
     * @param   array       the byte array to convert 
     * @return              a length*2 character string encoding the byte array       
     */  
    public static String toHex(byte[] array) {  
        BigInteger bi = new BigInteger(1, array);  
        String hex = bi.toString(16);  
        int paddingLength = (array.length * 2) - hex.length();  
        if (paddingLength > 0) {
        	return String.format("%0" + paddingLength + "d", 0) + hex;  
        }
        else {
        	return hex;  
        }
    }
    
    /**
     * 生成随机安全的盐，盐要求在8位以上，返回十六进制字符串
     * @Description TODO
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static String generateSalts() throws NoSuchAlgorithmException {  
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");  
        byte[] salt = new byte[SALT_BYTE_SIZE];  
        random.nextBytes(salt);
        return toHex(salt);  
    }
    
    
    public static byte[] generateSaltb() throws NoSuchAlgorithmException {  
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");  
        byte[] salt = new byte[SALT_BYTE_SIZE];  
        random.nextBytes(salt);
        return salt;  
    }
    
}
