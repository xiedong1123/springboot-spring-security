package com.ccl.base.utils.crypt;

import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * 基础加密组件 参考博客:http://snowolf.iteye.com/blog/382422
 * 官方网址：http://docs.oracle.com/javase
 * /6/docs/technotes/guides/security/SunProviders.html
 *测试地址：http://tool.oschina.net/encrypt?type=2
 *前端对应crypto.js
 * 
 * @version 1.0
 * @since 1.0
 */
public abstract class Coder {
	
	/** 
     * 盐的长度 
     */  
    public static final int SALT_BYTE_SIZE = 32 / 2;  
	
	public final static String[] hexDigits = { "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

	public static final String KEY_SHA = "SHA";

	public static final String KEY_MD5 = "MD5";

	/**
	 * MAC算法可选以下多种算法
	 * 
	 * <pre>
	 * HmacMD5 
	 * HmacSHA1 
	 * HmacSHA256 
	 * HmacSHA384 
	 * HmacSHA512
	 * </pre>
	 */
	public static final String KEY_MAC = "HmacMD5";

	/**
	 * BASE64解密
	 * 
	 * @param key
	 * @return String
	 * @throws Exception
	 */

	public static byte[] decryptBASE64(String key) throws Exception {
		return Base64.decode(key);
	}

	/**
	 * BASE64加密
	 * 
	 * @param key
	 * @return String
	 * @throws Exception
	 */
	public static String encryptBASE64(byte[] key) throws Exception {
		return Base64.encode(key);
	}

	/**
	 * MD5加密32位
	 * 
	 * @param data
	 * @param salt
	 * @return string
	 * @throws Exception
	 */
	public static String encrypt32MD5(byte[] data,String salt) throws Exception {

		MessageDigest md5 = MessageDigest.getInstance(KEY_MD5);
		md5.update(data);
		byte b[] = md5.digest();
		int i;
		StringBuffer buf = new StringBuffer("");
		for (int offset = 0; offset < b.length; offset++) {
			i = b[offset];
			if (i < 0) {
				i += 256;
			}
			if (i < 16) {
				buf.append("0");
			}
			buf.append(Integer.toHexString(i));
		}
		return buf.toString();
	}

	/**
	 * MD5加密32位
	 * 
	 * @param data
	 * @return byte[]
	 * @throws Exception
	 */
	public static byte[] encrypt32MD5Bits(byte[] data,String salt) throws Exception {

		MessageDigest md5 = MessageDigest.getInstance(KEY_MD5);
		md5.update(data);
		byte b[] = md5.digest();
		return b;
	}

	/**
	 * MD5加密16位
	 * 不推荐使用
	 * @param data
	 * @return String
	 * @throws Exception
	 */
	@Deprecated
	public static String encrypt16MD5(byte[] data,String salt) throws Exception {

		MessageDigest md5 = MessageDigest.getInstance(KEY_MD5);
		md5.update(data);
		byte b[] = md5.digest();
		int i;
		StringBuffer buf = new StringBuffer("");
		for (int offset = 0; offset < b.length; offset++) {
			i = b[offset];
			if (i < 0) {
				i += 256;
			}
			if (i < 16) {
				buf.append("0");
			}
			buf.append(Integer.toHexString(i));
		}
		return buf.toString().substring(8, 24);
	}

	/**
	 * MD5加密16位
	 * 不推荐使用
	 * @param data
	 * @return byte[]
	 * @throws Exception
	 */
	@Deprecated
	public static byte[] encrypt16MD5Bits(byte[] data,String salt) throws Exception {

		MessageDigest md5 = MessageDigest.getInstance(KEY_MD5);
		md5.update(data);
		byte b[] = md5.digest();
		return b;
	}

	/**
	 * SHA加密 encrypt：SHA-1 SHA-256 SHA-384 SHA-512  推荐使用SHA-256及以上加密方式
	 * 
	 * @param data
	 * @return String
	 * @throws Exception
	 */
	public static String encryptSHAHex(byte[] data, String encrypt,String salt)
			throws Exception {
		MessageDigest sha = MessageDigest.getInstance(encrypt);
		sha.update(data);
		byte b[] = sha.digest();
		int i;
		StringBuffer buf = new StringBuffer("");
		for (int offset = 0; offset < b.length; offset++) {
			i = b[offset];
			if (i < 0) {
				i += 256;
			}
			int m = i / 16;
			int n = i % 16;
			buf.append(hexDigits[m]).append(hexDigits[n]);
		}
		return buf.toString();
	}

	/**
	 * SHA加密 encrypt：SHA-1 SHA-256 SHA-384 SHA-512 推荐使用SHA-256及以上加密方式
	 * 
	 * @param data
	 * @return byte[]
	 * @throws Exception
	 */
	public static byte[] encryptSHA(byte[] data, String encrypt,String salt)
			throws Exception {

		MessageDigest sha = MessageDigest.getInstance(encrypt);
		sha.update(data);
		byte b[] = sha.digest();
		return b;

	}

	/**
	 * 初始化HMAC密钥:HmacMD5 HmacSHA1 HmacSHA256 HmacSHA384 HmacSHA512
	 * 必须随机生成，固定的密钥安全性很低
	 * @return
	 * @throws Exception
	 */
	public static String initMacKey(String encrypt) throws Exception {
		KeyGenerator keyGenerator = KeyGenerator.getInstance(encrypt);

		SecretKey secretKey = keyGenerator.generateKey();
		return encryptBASE64(secretKey.getEncoded());
	}

	/**
	 * HMAC加密:HmacMD5 HmacSHA1 HmacSHA256 HmacSHA384 HmacSHA512
	 * 
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String encryptHMACHex(byte[] data,String key,String encrypt,String salt) throws Exception {

		SecretKey secretKey = new SecretKeySpec(decryptBASE64(key), encrypt);
		Mac mac = Mac.getInstance(secretKey.getAlgorithm());
		mac.init(secretKey);
		byte b[] = mac.doFinal(data);
		StringBuffer buf = new StringBuffer("");
		int i;
		for (int offset = 0; offset < b.length; offset++) {
			i = b[offset];
			if (i < 0) {
				i += 256;
			}
			int m = i / 16;
			int n = i % 16;
			buf.append(hexDigits[m]).append(hexDigits[n]);
		}
		return buf.toString();
	}
	

	private final static class Base64 {

		private static char[] base64EncodeChars = new char[] { 'A', 'B', 'C',
				'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O',
				'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a',
				'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
				'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y',
				'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/' };

		private static byte[] base64DecodeChars = new byte[] { -1, -1, -1, -1,
				-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
				-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
				-1, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1, 63, 52, 53, 54, 55,
				56, 57, 58, 59, 60, 61, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3,
				4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20,
				21, 22, 23, 24, 25, -1, -1, -1, -1, -1, -1, 26, 27, 28, 29, 30,
				31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46,
				47, 48, 49, 50, 51, -1, -1, -1, -1, -1 };

		private Base64() {

		}

		public static String encode(byte[] data) {
			StringBuffer sb = new StringBuffer();
			int len = data.length;
			int i = 0;
			int b1, b2, b3;

			while (i < len) {
				b1 = data[i++] & 0xff;
				if (i == len) {
					sb.append(base64EncodeChars[b1 >>> 2]);
					sb.append(base64EncodeChars[(b1 & 0x3) << 4]);
					sb.append("==");
					break;
				}
				b2 = data[i++] & 0xff;
				if (i == len) {
					sb.append(base64EncodeChars[b1 >>> 2]);
					sb.append(base64EncodeChars[((b1 & 0x03) << 4)
							| ((b2 & 0xf0) >>> 4)]);
					sb.append(base64EncodeChars[(b2 & 0x0f) << 2]);
					sb.append("=");
					break;
				}
				b3 = data[i++] & 0xff;
				sb.append(base64EncodeChars[b1 >>> 2]);
				sb.append(base64EncodeChars[((b1 & 0x03) << 4)
						| ((b2 & 0xf0) >>> 4)]);
				sb.append(base64EncodeChars[((b2 & 0x0f) << 2)
						| ((b3 & 0xc0) >>> 6)]);
				sb.append(base64EncodeChars[b3 & 0x3f]);
			}
			return sb.toString();
		}

		public static byte[] decode(String str) {
			byte[] data = str.getBytes();
			int len = data.length;
			ByteArrayOutputStream buf = new ByteArrayOutputStream(len);
			int i = 0;
			int b1, b2, b3, b4;

			while (i < len) {

				/* b1 */
				do {
					b1 = base64DecodeChars[data[i++]];
				} while (i < len && b1 == -1);
				if (b1 == -1) {
					break;
				}

				/* b2 */
				do {
					b2 = base64DecodeChars[data[i++]];
				} while (i < len && b2 == -1);
				if (b2 == -1) {
					break;
				}
				buf.write((int) ((b1 << 2) | ((b2 & 0x30) >>> 4)));

				/* b3 */
				do {
					b3 = data[i++];
					if (b3 == 61) {
						return buf.toByteArray();
					}
					b3 = base64DecodeChars[b3];
				} while (i < len && b3 == -1);
				if (b3 == -1) {
					break;
				}
				buf.write((int) (((b2 & 0x0f) << 4) | ((b3 & 0x3c) >>> 2)));

				/* b4 */
				do {
					b4 = data[i++];
					if (b4 == 61) {
						return buf.toByteArray();
					}
					b4 = base64DecodeChars[b4];
				} while (i < len && b4 == -1);
				if (b4 == -1) {
					break;
				}
				buf.write((int) (((b3 & 0x03) << 6) | b4));
			}
			return buf.toByteArray();
		}
	}
	
	public static String generateSalt() throws NoSuchAlgorithmException {  
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");  
        byte[] salt = new byte[SALT_BYTE_SIZE];  
        random.nextBytes(salt);  
  
        return toHex(salt);  
    }  
	
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

	public static void main(String[] args) throws Exception {
		String inputStr = "刘成勇下";
		// base64
		String base64Result = Base64.encode(inputStr.getBytes());
		System.out.println(base64Result);
		System.out.println(new String(Base64.decode(base64Result)));
		// md5
		//System.out.println(Coder.encrypt32MD5((inputStr.getBytes())));
		//System.out.println(Coder.encrypt16MD5(inputStr.getBytes()));
		// sha1
		//System.out.println(Coder.encryptSHA(inputStr.getBytes(), "SHA"));
		//System.out.println(Coder.encryptSHA(inputStr.getBytes(), "SHA-1"));
		//System.out.println(Coder.encryptSHA(inputStr.getBytes(), "SHA-256"));
		//System.out.println(Coder.encryptSHA(inputStr.getBytes(), "SHA-384"));
		//System.out.println(Coder.encryptSHA(inputStr.getBytes(), "SHA-512"));
		// hmac
		String key = Coder.initMacKey("HmacMD5");
		System.out.println("----key:"+key);
		//System.out.println(Coder.encryptHMACHex(inputStr.getBytes(), "1111", "HmacMD5"));
		//System.out.println(Coder.encryptHMACHex(inputStr.getBytes(), "1111", "HmacSHA1"));
		//System.out.println(Coder.encryptHMACHex(inputStr.getBytes(), "1111", "HmacSHA384"));
		//System.out.println(Coder.encryptHMACHex(inputStr.getBytes(), "1111", "HmacSHA512"));
	}
}
