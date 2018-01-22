package com.ccl.base.utils.converter;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.ccl.base.utils.crypt.AesCBC;
/**
 * 
 * @ClassName CryptFastJsonHttpMessageConverter
 * @Description 通用的手机端接口加解密json转换器
 * @author liuchengyong
 * @Date 2016-11-29 上午9:20:39
 * @version 1.0.0
 */
public class CryptFastJsonHttpMessageConverter extends AbstractHttpMessageConverter<Object> {
	
	
	private static Logger logger = LoggerFactory.getLogger(CryptFastJsonHttpMessageConverter.class);

	
	public final static Charset UTF8   = Charset.forName("UTF-8");

    private Charset charset  = UTF8;
    private SerializerFeature[] features = new SerializerFeature[0];
    
    /**
     * 指定返回json数据的key的名称
     */
    private String returnKeyName = "key";
    
    
    public CryptFastJsonHttpMessageConverter() {
		super(new MediaType("application","crypt-json",UTF8));
	}
	
	@Override
	protected boolean supports(Class<?> clazz) {
		return true;
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
        String string = IOUtils.toString(in, "utf-8");
        String decrypt = AesCBC.getInstance().decrypt(string, "1234567890098765");
        if(StringUtils.isBlank(decrypt)){
        	logger.info("---------------------解密失败-----------------------------");
        	return null;
        }
		
		return JSON.parseObject(decrypt.getBytes(),0,decrypt.getBytes().length, charset.newDecoder(), clazz);
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
        String encrypt = AesCBC.getInstance().encrypt(js, "1234567890098765");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(returnKeyName,encrypt);
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
