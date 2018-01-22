/**
 ***********************************************************************
 * All rights Reserved, Designed By changhong
 * @Title: O2oExcpetion.java   
 * @Package com.changhong.framework.common.utils   
 * @Description: TODO   
 * @author: liuchengyong     
 * @date: 2016-12-7 上午10:40:29   
 * @version V1.0 
 * @Copyright: 2016 www.changhong.com Inc. All rights reserved. 
 ***********************************************************************
 */
package com.ccl.base.utils;

/**
 * 主要用于系统异常的时候，所有的异常均要转换成该异常，最后由统一异常来处理
 * @ClassName O2oExcpetion
 * @Description TODO
 * @author liuchengyong
 * @Date 2016-12-7 上午10:40:29
 * @version 1.0.0
 */
@SuppressWarnings("serial")
public class O2oExcpetion extends RuntimeException {
	/**
	 * @Field @serialVersionUID : TODO
	 */
	private Integer errorCode; 	//错误编码
	private Object data; //其他信息 
	
	/**
	 * @Description TODO
	 * @param msg
	 * @param errorCode
	 */
	public O2oExcpetion(String msg, Integer errorCode) {
		super(msg);
		this.errorCode = errorCode;
	}
	
	/**
	 * @Description TODO
	 * @return the errorCode
	 */
	public Integer getErrorCode() {
		return errorCode;
	}
	
	/**
	 * @Description TODO
	 * @param errorCode the errorCode to set
	 */
	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}


	/**
	 * @Description TODO
	 * @return the data
	 */
	public Object getData() {
		return data;
	}


	/**
	 * @Description TODO
	 * @param data the data to set
	 */
	public void setData(Object data) {
		this.data = data;
	}
}
