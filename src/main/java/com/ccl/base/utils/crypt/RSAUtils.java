package com.ccl.base.utils.crypt;

import java.io.ByteArrayOutputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 
 * @ClassName：RSAUtils
 * @Description：RSA 加解密
 * @Author：xiedong
 * @Date：2017年12月20日下午2:24:56
 * @version：1.0.0
 */
public class RSAUtils {
	
	private static final Logger logger = LoggerFactory.getLogger(RSAUtils.class);
	
	private static final String KEY_ALGORITHM = "RSA";
	private static final String SIGNATURE_ALGORITHM = "MD5withRSA";

	// 公钥
	private static final String PUBLIC_KEY = "RSAPublicKey";
	// 私钥
	private static final String PRIVATE_KEY = "RSAPrivateKey";


	/**
	 * 初始化密钥
	 * 
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> initKey() {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			// 随机生成密钥对
			KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
			// 按照指定字符串生成密钥对
			// SecureRandom secureRandom = new SecureRandom("我是字符串".getBytes());
			// keyPairGen.initialize(1024, secureRandom);

			keyPairGen.initialize(1024);
			KeyPair keyPair = keyPairGen.generateKeyPair();
			// 公钥
			RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
			// 私钥
			RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
			map.put(PUBLIC_KEY, publicKey);
			map.put(PRIVATE_KEY, privateKey);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("初始化密钥异常" + e);
		}
		return map;
	}
	
	/** *//** 
     * <p> 
     * 获取私钥 
     * </p> 
     *  
     * @param keyMap 密钥对 
     * @return 
     * @throws Exception 
     */  
    public String getPrivateKey(Map<String, Object> keyMap)  
            throws Exception {  
        Key key = (Key) keyMap.get(PRIVATE_KEY);  
        return BASE64Coder.encode(key.getEncoded());  
    }  
  
    /** *//** 
     * <p> 
     * 获取公钥 
     * </p> 
     *  
     * @param keyMap 密钥对 
     * @return 
     * @throws Exception 
     */  
    public String getPublicKey(Map<String, Object> keyMap)  
            throws Exception {  
        Key key = (Key) keyMap.get(PUBLIC_KEY);  
        return BASE64Coder.encode(key.getEncoded());  
    }
	
	
	/**
	 * 公钥加密
	 * 
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public String encryptByPublicKey(String data, String key) {
		try {
			// 取得公钥
			X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(Coder.decryptBASE64(key));
			KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
			Key publicKey = keyFactory.generatePublic(x509KeySpec);

			Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);

			byte[] encryptedData = data.getBytes("utf-8");
			int inputLen = encryptedData.length;
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			int offSet = 0;
			byte[] cache;
			int i = 0;
			// 对数据分段加密
			while (inputLen - offSet > 0) {
				if (inputLen - offSet > 117) {
					cache = cipher.doFinal(encryptedData, offSet, 117);
				} else {
					cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
				}
				out.write(cache, 0, cache.length);
				i++;
				offSet = i * 117;
			}
			byte[] decryptedData = out.toByteArray();
			out.close();
			return Coder.encryptBASE64(decryptedData);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("公钥加密异常!");
		}
		return null;
	}

	/**
	 * 公钥解密
	 * 
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public String decryptByPublicKey(String data, String key) {
		// 对密钥解密
		try {
			byte[] keyBytes = Coder.decryptBASE64(key);

			// 取得公钥
			X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
			KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
			Key publicKey = keyFactory.generatePublic(x509KeySpec);

			Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
			cipher.init(Cipher.DECRYPT_MODE, publicKey);

			byte[] encryptedData = Coder.decryptBASE64(data);

			int inputLen = encryptedData.length;
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			int offSet = 0;
			byte[] cache;
			int i = 0;
			// 对数据分段解密
			while (inputLen - offSet > 0) {
				if (inputLen - offSet > 128) {
					cache = cipher.doFinal(encryptedData, offSet, 128);
				} else {
					cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
				}
				out.write(cache, 0, cache.length);
				i++;
				offSet = i * 128;
			}
			byte[] decryptedData = out.toByteArray();
			out.close();

			return new String(decryptedData, "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("公钥解密异常");
		}
		return null;
	}

	/**
	 * 私钥加密
	 * 
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public String encryptByPrivateKey(String data, String key) {
		try {

			// 对密钥解密
			byte[] keyBytes = Coder.decryptBASE64(key);

			// 取得私钥
			PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
			KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
			Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);

			Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
			cipher.init(Cipher.ENCRYPT_MODE, privateKey);

			byte[] encryptedData = data.getBytes("utf-8");
			int inputLen = encryptedData.length;
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			int offSet = 0;
			byte[] cache;
			int i = 0;
			// 对数据分段加密
			while (inputLen - offSet > 0) {
				if (inputLen - offSet > 117) {
					cache = cipher.doFinal(encryptedData, offSet, 117);
				} else {
					cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
				}
				out.write(cache, 0, cache.length);
				i++;
				offSet = i * 117;
			}
			byte[] decryptedData = out.toByteArray();
			out.close();
			return Coder.encryptBASE64(decryptedData);

		} catch (Exception e) {
			e.printStackTrace();
			logger.info("私钥加密异常");
		}
		return null;
	}

	/**
	 * 私钥解密
	 * 
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public String decryptByPrivateKey(String data, String key) {
		try {
			// 对密钥解密
			byte[] keyBytes = Coder.decryptBASE64(key);

			// 取得私钥
			PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
			KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
			Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);

			Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
			cipher.init(Cipher.DECRYPT_MODE, privateKey);

			byte[] encryptedData = Coder.decryptBASE64(data);

			int inputLen = encryptedData.length;
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			int offSet = 0;
			byte[] cache;
			int i = 0;
			// 对数据分段解密
			while (inputLen - offSet > 0) {
				if (inputLen - offSet > 128) {
					cache = cipher.doFinal(encryptedData, offSet, 128);
				} else {
					cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
				}
				out.write(cache, 0, cache.length);
				i++;
				offSet = i * 128;
			}
			byte[] decryptedData = out.toByteArray();
			out.close();

			return new String(decryptedData, "utf-8");

		} catch (Exception e) {
			e.printStackTrace();
			logger.info("私钥解密异常");
		}
		return null;
	}

	/**
	 * 用私钥对信息生成数字签名
	 * 
	 * @param data
	 *            加密数据
	 * @param privateKey
	 *            私钥
	 * 
	 * @return
	 * @throws Exception
	 */
	public String sign(byte[] data, String privateKey) throws Exception {
		// 解密由base64编码的私钥
		byte[] keyBytes = Coder.decryptBASE64(privateKey);

		// 构造PKCS8EncodedKeySpec对象
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);

		// KEY_ALGORITHM 指定的加密算法
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);

		// 取私钥匙对象
		PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);

		// 用私钥对信息生成数字签名
		Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
		signature.initSign(priKey);
		signature.update(data);

		return Coder.encryptBASE64(signature.sign());
	}

	/**
	 * 校验数字签名
	 * 
	 * @param data
	 *            加密数据
	 * @param publicKey
	 *            公钥
	 * @param sign
	 *            数字签名
	 * 
	 * @return 校验成功返回true 失败返回false
	 * @throws Exception
	 * 
	 */
	public boolean verify(byte[] data, String publicKey, String sign) throws Exception {

		// 解密由base64编码的公钥
		byte[] keyBytes = Coder.decryptBASE64(publicKey);

		// 构造X509EncodedKeySpec对象
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);

		// KEY_ALGORITHM 指定的加密算法
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);

		// 取公钥匙对象
		PublicKey pubKey = keyFactory.generatePublic(keySpec);

		Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
		signature.initVerify(pubKey);
		signature.update(data);

		// 验证签名是否正常
		return signature.verify(Coder.decryptBASE64(sign));
	}

	private RSAUtils() {

	}

	private static final RSAUtils rsa = new RSAUtils();

	public static RSAUtils getInstance() {
		return rsa;
	}

	public static void main(String args[]) throws Exception {
		/*RSAUtils.getInstance().initKey();
		StringBuilder sb = new StringBuilder();
		int i = 0;
		while (i < 1000) {
			sb.append("a");
			i++;
		}
		String a = sb.toString();
		System.out.println("私钥加密，公钥解密");
		String _tmp = RSAUtils.getInstance().encryptByPrivateKey(a, PRIVATE_KEY);
		if (RSAUtils.getInstance().decryptByPublicKey(_tmp, PUBLIC_KEY).equals(a)) {
			System.out.println("pass");
		}

		System.out.println("公钥加密，私钥解密");
		String _tmp1 = RSAUtils.getInstance().encryptByPublicKey(a, PUBLIC_KEY);
		if (RSAUtils.getInstance().decryptByPrivateKey(_tmp1, PRIVATE_KEY).equals(a)) {
			System.out.println("pass");
		}*/
		
		Map<String, Object> initKey = RSAUtils.getInstance().initKey();
		String publicKey = RSAUtils.getInstance().getPublicKey(initKey);
		System.out.println("公钥:" + publicKey);
		String privateKey = RSAUtils.getInstance().getPrivateKey(initKey);
		System.out.println("私钥:" + privateKey);
		String gy = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDb9n4GsyX0Q0CvEMz+kBvoCSVysuu6qh/MRR1u+wTgkKlHsi62furkOAGfee8+aibT4uZbOBGtvVf7QeNEgAPcL9zRNvN5FLaMQJCq4OOd+Qtl+IABu8sdKPUf6TPtlziA9Ojbnvrm90ZLJNxCK6CaJnAtjkzhzQ3lD6+CJ6CF7QIDAQAB";
		String xx = "dQ9v/fvLO6h4FwlBVKVZ58s6sx8Y1BU8mha1ZyFKofw+ktKWDak8/AwhvdDc8eIXwNm38SnahoBZT8mEi+NFDBcemHcITU72CfetHiyZPLc8tEZqGJDdkKrv8IrqW6KA5H15UfvjNuMHLnGlGSxszYWHQe1GhgjJ1FAMdCf0YJM=";
		boolean verify = RSAUtils.getInstance().verify("{page:1,limit:20}".getBytes(), gy, xx);
		System.out.println(verify);
		
		/*privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAIJdIIyjCx2dyM+niPDZIVf7hS14tLGEgYi0vV9QOl++qli4t6Ry8zToicVzy2TcIVe7keT/33VxhFs0wx31oRMJSBz3otNbGDBgLrs1iy+WAyxLNzDI5aBuI22+cMHqHxz4BQ2/e9SG06sSUC3sfHoW5Vt3Q3oZ2Z+EXepHl6a9AgMBAAECgYBIdhM9mPL4x1dzdSA9H7PU/82K9OkbMxf9MNmCPUn1Jjnxjtsk4XAmHItX4YFQ0rsF1RDHRSKkWTDEsZMy9/FLoFINtkhXGXv8usJ7RFhvo28d0m1yT8dYJug6m2EVfPhToNTQ4h4ey++rJS/cw4Nj7NsTt87qlA7ArI9M5pCAUQJBAN8+MCIUbTcieYu2ltDpFKhvARX0ePcPrvnDmA4Iio9mWgKRQCr0C21c5hHTrM7V+0S8XGGb2ts0+cj9CDnaovsCQQCVfg/4Oth66UYd3uYjHD6LbTL71iatiKP3iLl4Gr0BEL4POfCrVbb7ZZKQ7HtbjEzznkvjvZZlrc5iWn3iPe+nAkEAyNl3jsHX/RncRxIQoIaDmoK8dFdOmeGXaIlMfZu7pgbNBrBODhchTNSe/fz25eRpO89IQl1py3zfGrBkvUBzDwJAf8+oF7M4It4O9Lr+SEZ9svdJ/8a4iuWoSpq699Tkg2StGpePDO3b5FSTWxTU4xCxOhi9CZmuXICdU7nKpwQNQQJAPM/zkoyD7B7GcPfKo14NPnSnBakfXTrax8FEuBlrUUe4h1VJnXfPdIDDgR3GLO2wedm7BEs117vcRRPZE97KLg==";
		String encryptByPublicKey = RSAUtils.getInstance().encryptByPublicKey("11111111111111",publicKey);
		String decryptByPrivateKey = RSAUtils.getInstance().decryptByPrivateKey("LOZ12zKO+QFx3VxBJjbNLL21lzsH3lLyDHxtRJr2azhZdTBOf3tOLRmk9OZSTpHj3UeAUutSn1WJmv0DizJIVYlaUAAndoOg7wUGdJThMqAFI61c0kQyBo96Rz9J9peYX/pI5PpC3+EyCknHBooPsQEeK1wLrj8kDhd0eO59Prc=", privateKey);
		System.out.println(decryptByPrivateKey);*/
	}
}
