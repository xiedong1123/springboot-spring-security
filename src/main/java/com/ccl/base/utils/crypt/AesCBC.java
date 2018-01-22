package com.ccl.base.utils.crypt;

import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * AES 是一种可逆加密算法，对用户的敏感信息加密处理 对原始数据进行AES加密后，在进行Base64编码转化；
 */
public class AesCBC {
	
	private static Logger logger = LoggerFactory.getLogger(AesCBC.class);
	
	/*
	 * 加密用的Key 可以用26个字母和数字组成 此处使用AES-128-CBC加密模式，key需要为16位。
	 */
	private static String sKey = "0000chenghong000";
	private static String ivParameter = "bicycle000000000";
	/**
	 * 算法
	 */
	private static final String ALGORITHMSTR = "AES/CBC/PKCS7Padding"; 
	
	private static AesCBC instance = null;

	private AesCBC() {

	}

	public static AesCBC getInstance() {
		if (instance == null) {
			instance = new AesCBC();
		}
		return instance;
	}

	/**
	 * 
	 * @MethodName：encrypt
	 * @param sSrc 明文
	 * @param sKey 秘钥
	 * @param ivParameter iv
	 * @return
	 * @throws Exception
	 * @ReturnType：String
	 * @Description：加密
	 * @Creator：xiedong
	 * @CreateTime：2017年7月11日下午1:57:31
	 * @Modifier：
	 * @ModifyTime：
	 */
	public String encrypt(String sSrc,String ivParameter){
		//添加PKCS7Padding加密
		try {
			Security.addProvider(new BouncyCastleProvider());
			Cipher cipher = Cipher.getInstance(ALGORITHMSTR);
			byte[] raw = sKey.getBytes();
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());// 使用CBC模式，需要一个向量iv，可增加加密算法的强度
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
			byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));
			return BASE64Coder.encode(encrypted);// 此处使用BASE64做转码。
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * @MethodName：decrypt
	 * @param sSrc 密文
	 * @param sKey 秘钥
	 * @param ivParameter iv
	 * @return
	 * @throws Exception
	 * @ReturnType：String
	 * @Description：解密
	 * @Creator：xiedong
	 * @CreateTime：2017年7月11日下午1:55:55
	 * @Modifier：
	 * @ModifyTime：
	 */
	public String decrypt(String sSrc,String ivParameter){
		try {
			//添加PKCS7Padding解密
			Security.addProvider(new BouncyCastleProvider());
			byte[] raw = sKey.getBytes("ASCII");
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance(ALGORITHMSTR);
			IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
			byte[] encrypted1 = BASE64Coder.decodeb(sSrc);// 先用base64解密
			byte[] original = cipher.doFinal(encrypted1);
			String originalString = new String(original, "utf-8");
			return originalString;
		} catch (Exception e){
			logger.error("-------------解密失败-------------------", e);
			e.printStackTrace();
			return null;
		}
	}

	public static void main(String[] args) throws Exception {
			
			
			// 需要加密的字串
	/*		String cSrc = "bicycle2016,/sys/initParam";
			System.out.println(cSrc);
			// 加密
			long lStart = System.currentTimeMillis();
			String enString = AesCBC.getInstance().encrypt(cSrc,ivParameter);
			System.out.println("加密后的字串是：" + enString);
			
			long lUseTime = System.currentTimeMillis() - lStart;
			System.out.println("加密耗时：" + lUseTime + "毫秒");*/
			// 解密
			//lStart = System.currentTimeMillis();
			String deString = AesCBC.getInstance().decrypt("T/JktiTFVy+jw8WN7+2bl8slDViTfSkqQnWp6X3bKQ2ZugWZnIalzLqfUnWyQcnEqICwcPYKY74wjapi8diewDRIdsWdk8Zxs4v2SUIwDR1c9/l/DW1htFmWcTTgQmxqlSVre7M+oiVzEX5wjTw1osseOlIEqHWTc43SvShvBA0ujO1gLp0E1Vs4hBggNay9y0pVDKOgxxHw+syBhFJ6dlIboIstoz9633jtrWHgWFlNeKFoEQEZ9L1dpf6nuh52Vx8bPV4EsRiu6NDzpbQEJ/CRxGhxJOXM9gFPPCfafLOfwm88OUFjuknoIL+JSDDs5YpZBWyl1fgaEBgU85QDBNsZDKAaeCgVg6FvynpI68bxoawNVL9VFn2zrNrProqDYk0XUdIJzUBBQ+aije7C9tYEg5Whf/ja963PrelcpOltvm10c+hTqB0GIjA8UB7RXQr9KqeTvO25OCI0EfDL/L1NzUqKodFzcnhztWXB81V6kLKbsXwpTPnswxzltJbrOKKNGKMRWqGTaimiZWJYjkJXT4eX7cqxzqc04MXMmMz5QUpzM/jHTZkdWxX3wAs8ABQuazXUCky64QGhek14TkvwQ1MISsxADy3BcNHshzX/1M7UANbR6kQsHag9MQHf2sIzJf5uQtSwnJTNQaQHvqwNerkzWavmapWRFoPfKNP3g9ylO5nSGA8BwLLh6CD1NeVMmtVClRPwrTvPTspz14+m+z7r5yqk4sCXXI+Mo5L8fcW4ehBjSD/6LuuKQwqsC1zdDJ6GAo26KVADH+oswSkS5LaX4QRiOh91704p0jubWERxovNyQT10nHdSZK2UBDHVZGKC1X5lWTFUCDC+UDBEPCgQ5y5Vvv5RKjfDTyP+pTlRSobWTCGzG5R3mXSjbHU6M8Ynm2au9jfvh0CVknpL2+JlQIUIimgDJTzkEc9coAguoVu7OBFewLXYwn8ZFV5ZGTfnmt16ZUCBPhYz5wcETXZaNA+cU3hQHzekSCqMCeI6ND/S8VKafLpO8tN0lcHpdxk8Hof2gf20YY9l7TJ9uRXqnCxZXorFG1YvnaUQd1ozLyZjGxGh8JS2HFrUx7I0VgFkQ6R/qrpj0QtGNCyWbaxdKWNN09nK2tBukZmu0ByuYUdRN5c/EhaeRGEfn/V+3u52zmGqsIp1kaAIM+/2Ny9X/RzEDCESV+FauDZ+DHVTT1qJYF1hYrbnef/po5pEzm+NmoT6dkGLiPtRG4DitwBbn/gqheit0CjQ8kUbmqpWVH7n/9hztugBicW2YfnbQ3xRHnqpYyjjaaD/+e7PXTvnN/SmXrFdTq4LIKq8XWzERlfaXkBKz9rejP9lauVNUBkAEqTz2Hi+ebWt8NKE1le85HmIeO2Xo5E9/vZYv20AD1C+FtBkOAZ+SCOdjITkPTDtn+o90+xVcrnTFcj9H96uyDmEnrNUZNta6nGzpF+uysEjJgOQGaCzuum6PIeoncezGX6QK2g5t5OjhoY4/zwpSftAO4XvsP+B7+yY+irC6UtRw2B1KcBn33VxFv5CFM76on0lPgYJePpsXPFXPz/T+x+tmYr3yUXPeKinP7uW52tlJ7ZKkyZrb14wf9PMw0Rxl+uRP5xg/egKOZJohAI8xBpQRr7bVhJLisbXBwz757h2FEtPpy3GfIJFI3w+/rv57A2acbAJE/NYFg7xGqofcBUIkjROv2VFVSw2YI5QHfc+3SGzcUt2CQrmzYCSF6PnKnNc19J83hjO7euhXehWmFVOgylenZfa2H2YgrPoCjoEwlR3F6uAvJDXgDCg2o4PZRv4TdINuQggxVp0yNQvbk9FQv8q+aoirwMh6y+pZt1teLNZQJRLN9vpFceb88xx9epRD7jNhZTOv00kjYAu5cLKrQdYzq7TocrNSAz1SPRmrDJ0kid+7QKvOctlXuT/qILFnMMJOEtZvXwDJhuzR54bJEvdfIxjpZcckLQliqKZad6aOkWL20hADvKSPfPXeg7l7yCVSwGITUGxHQK6nPiSXeFx65Yzx5MX0iXbw447C97M1VBxOIhmUKQsrfpAvbf04+RlUl6CHSQ6j7Y7FdboFmcw1Uu/gWlt+Z4omGO3hBv/rPAR1rk/yGNtFTSpoeKpJDtIeHPPGcKzb/vQrYJeey2m1EEJe/Tfzwi7yJfMSfXY1ZXVc9gmC1dp7TqSW9INOsREXBQy6s8ukX7T136swQQWt8p8Nv1jmKx6I8eJxOAWgmIKhLwWzSwkurzl47CVhRL7YnIUsMyp5l5kGwT15KOWC6stsdPz9l5/0rcFn1xchDTs8ErUaKnEtvfQQgebH8mMEZ3ihJn7b69N6x8BJ5NGDv23gYOoq8/d6HEiftIJSS6vh70OnjQ4Dal9dypkc6l3m1trTss3DFmaIe5EBKciTJg4lhyLGC52qgFozGLB629pmQiXAbdnKggpGg7YkX/MOyCHJxaOyUyjm2+3Z8Oo3F4JaOBg95TFbATeGVhGRO8vvf1Zifs3h7b0UHYf76MBz/dwnEPHW3kzIkqqja3490hiGZAe+kSS3r9QctihkLqMUGaK3Z1L+5pC2eaFlmO92AqmnYSiCSQ6CdjsTWszbmr1ejaf9w9dbujaElvrMrW3b7jYg+Qle2QqxSzyja+ZEk1VYsjb3xh5kcH1t7K6Fj/+NpGKrVIWxP2BDbB3D/bTf61d0j+Gk+KFr0oNKAv53VWjftsmq0FyXhsdsPMfT+uOPgs0io+yHPvq6r2pGtg8lbkYYNu8HSMA96rRptDwfxkncOHc0BHCqSEY+XeuKUe5Biiiz/01R1eSV85PpeK4twFHKOWY5oDL4yD3htOqW6AN8WeyXkS3/rZ64BUxQcU1thsaZxORAVYLozWoRxJhZ9Q2MtE9gSBpsxJFE9xRfzXqgImd51vZQp5GjYKsnyFxrlKGr1upspU6LCFytPFvEFOjvcYDKCHQi66k6LtZk3BqhOX1sjuKF0rDrocRy4v4nCBOT/wUOhUxTY+tHSLICjJH3EMJtE5vaY/Grutbs+tOKrXT/X7hrkvc8gozhqUy/P9aLtRMF76Rtcr5vG8bCQW2jeejSp/i7jlqT1MhN+wdSj0PZaZyRlh6Kawyy8ouVYVPb5orpMsMvpcmrvnJcNMElH+tyhmVeInZBDcdpgQz20X8lRBpiK+yPbSLrDIoZxXz1leCsf3/+q4x9JWh9B7vE0/Z0A9XgiqbVSwT/bIMdidIaxdVPcTVi2ZARdsFTff+vlRlElaf2NZwDWqQJcF1AsQojztRVqiGNGYXTU61VYTKgYg3QWnKwAXz9CNxUfNcy8uUsGzcI71Kr6M3kPFC6nw0oA2m6s+sv58qequHNTPBdnKOyW9IRlwYxBfpad1aB6hgUXI6nIdiGUIoEGj3yMLGO8MTqXB2HMgChJmT4cUR/TOkwBPUPA7lYCXiu07sPXgc5iMtlhbxcqNAmeq7KexFocYLEl8fWjG1SxRPKynDYo9ImiE1ll52hZEHsHfF3b+JpEhy+AVIOJfzG5Q2Emu+kLZB4AngGd04vUXIWYLFH3lt+pjyy4xTwJBsRUlVBMjnvisRLU6kZsTYR4cIvT3PFZ4p9WAl8TjXhmrqVMjn2756all/NQTMPpUHiFP/pwXy4k/OuT/3/btm+1iWibZ6+yPD+hOdDIhuwcQo9fRs9HSbQgRHcX93S/NiocSAJxgiaNi5ImgXdi8at5qGGfxuAbnbS+4vaFj08W7tKko0iVcEQp5XdqpX9gABG6R88rmhNu9E3ayVg3t0FZ4ueZhrDMyY10ZFmyPcOrSVhyzxKOh5RgXaDhps3NS+8wo=","1234567890098765");
			System.out.println("解密后的字串是：" + deString);
			//lUseTime = System.currentTimeMillis() - lStart;
			//System.out.println("解密耗时：" + lUseTime + "毫秒");
	}
}
