package com.ccl.base.utils.crypt;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;

import org.apache.commons.codec.binary.Base64;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.changhong.cacsdk.api.MainApi;
import com.changhong.cacsdk.entity.CryptEntity;
/**
 * 
 * @ClassName CryptFastJsonHttpMessageConverter
 * @Description 通用的手机端接口加解密json转换器
 * @author liuchengyong
 * @Date 2016-11-29 上午9:20:39
 * @version 1.0.0
 */
public class CryptFastJsonHttpMessageConverter extends AbstractHttpMessageConverter<Object> {

	private static final MainApi api = new MainApi();
	private static final Base64 BASE_64 = new Base64();
	//	private static final BASE64Encoder BASE64ENCODER =new BASE64Encoder();
	//	private static final BASE64Decoder BASE64DECODER =new BASE64Decoder();
	
	public final static Charset UTF8   = Charset.forName("UTF-8");

    private Charset charset  = UTF8;
    private SerializerFeature[] features = new SerializerFeature[0];
    
    /**
     * 指定返回json数据的key的名称
     */
    private String returnKeyName;
    
    public CryptFastJsonHttpMessageConverter() {
		super(new MediaType("application","crypt-json",UTF8));
	}
    
	
	@Override
	protected boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return true;
	}
	
	public static  byte[] inputStreamToByte(InputStream inputStream) throws IOException {
		ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int ch;
		while ((ch = inputStream.read(buffer)) != -1) {
			bytestream.write(buffer, 0, ch);
		}
		byte data[] = bytestream.toByteArray();
		bytestream.close();
		return data;
	}
	
	/**
	 * 
	 * @方法名：readInternal
	 * @param clazz
	 * @param inputMessage
	 * @return
	 * @throws IOException
	 * @throws HttpMessageNotReadableException
	 * @see org.springframework.http.converter.AbstractHttpMessageConverter#readInternal(java.lang.Class, org.springframework.http.HttpInputMessage)
	 * @说明：解密处理
	 * @创建人：
	 * @创建日期：2016年11月25日下午3:13:31
	 * @修改人：
	 * @修改日期：
	 */
	@Override
    protected Object readInternal(Class<? extends Object> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        InputStream in = inputMessage.getBody();
        byte[] inputStreamToByte = inputStreamToByte(in);
        byte[] bytes = BASE_64.decode(inputStreamToByte);
        CryptEntity aesDecrypt = api.aesDecrypt(bytes, null);
        return JSON.parseObject(aesDecrypt.getParam(),0,aesDecrypt.getParam().length, charset.newDecoder(), clazz);
    }
	
	/**
	 * 
	 * @方法名：writeInternal
	 * @param obj
	 * @param outputMessage
	 * @throws IOException
	 * @throws HttpMessageNotWritableException
	 * @see org.springframework.http.converter.AbstractHttpMessageConverter#writeInternal(java.lang.Object, org.springframework.http.HttpOutputMessage)
	 * @说明：加密处理
	 * @创建人：
	 * @创建日期：2016-11-24下午6:26:55
	 * @修改人：
	 * @修改日期：
	 */
    @Override
    protected void writeInternal(Object obj, HttpOutputMessage outputMessage) throws IOException,HttpMessageNotWritableException {
        OutputStream out = outputMessage.getBody();
        String js = JSON.toJSONString(obj, features);
        CryptEntity aesEncrypt = api.aesEncrypt(js.getBytes(charset), null);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(returnKeyName,  BASE_64.encode(aesEncrypt.getParam()));
        logger.info("--------------加密后----------------"+jsonObject.toJSONString());
        out.write(jsonObject.toJSONString().getBytes());
    }

	/**
	 * @Description 注入返回数据的keyName
	 * @param returnKeyName the returnKeyName to set
	 */
	public void setReturnKeyName(String returnKeyName) {
		this.returnKeyName = returnKeyName;
	}
	    
}
