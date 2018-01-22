/**
 ***********************************************************************
 * All rights Reserved, Designed By changhong
 * @Title: GlobalExceptionResolver.java   
 * @Package com.changhong.framework.clients.common.resolver   
 * @Description: 全局异常解析器：针对普通请求、ajax请求、及手机app请求做相应的处理 
 * @author: liuchengyong     
 * @date: 2016-11-25 下午4:34:22   
 * @version V1.0 
 * @Copyright: 2016 www.changhong.com Inc. All rights reserved. 
 ***********************************************************************
 *//*
package com.ccl.base.utils.resolver;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import com.alibaba.fastjson.JSONObject;
import com.ccl.base.utils.AjaxResult;
import com.ccl.base.utils.O2oExcpetion;

*//**
 * @ClassName GlobalExceptionResolver
 * @Description 全局异常解析器
 * @author liuchengyong
 * @Date 2016-11-25 下午4:34:22
 * @version 1.0.0
 *//*
public class GlobalExceptionResolver extends SimpleMappingExceptionResolver {
	
	//支持jsonp
    private String callback;
    
    //来自app端的标识符
    private String appTag;

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionResolver.class);

	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		
		//返回的结果
		AjaxResult  rs = null;
		
		//业务异常
		if(ex instanceof O2oExcpetion){
			O2oExcpetion o2oEx = (O2oExcpetion) ex;
			rs = new AjaxResult(false,o2oEx.getErrorCode(),o2oEx.getMessage(),o2oEx.getData());
		}else{
			rs = new AjaxResult(false,"系统错误",1001);
		}
		
		String viewName = determineViewName(ex, request);
		String rsJson = JSONObject.toJSONString(rs);
		
		if(isFromApp(request)){ //来自手机端的请求
			LOGGER.debug("来自手机端的请求");
			if(isJsonCrypt(request)){
				LOGGER.debug("需要加密返回的手机端请求");
				try {
					sendCryptResponseData(MainApiUtils.encryptString(rsJson),response);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}else{
				sendResponseData(rsJson,response);
			}
			return null;
		}else if(isJson(request)){  //一般的http+json的请求，通常为ajax
			LOGGER.debug("json请求");
			if(isAjaxJsonp(request)){
				LOGGER.debug("支持的jsonp请求");
				sendResponseData(callback+"("+rsJson+")",response);
			}else{
				sendResponseData(rsJson,response);
			}
			return null;
		}
		
		Integer statusCode = determineStatusCode(request, viewName);
		if(statusCode != null) {
			applyStatusCodeIfPossible(request, response, statusCode);
		}
		LOGGER.debug("返回视图的请求");
		return getModelAndView(viewName, ex, request);
	}

	*//**
	 * 
	 * @Description 是否是json格式请求，来自app或者ajax请求
	 * @return
	 *//*
	private boolean isJson(HttpServletRequest request) {
		if (!(request.getHeader("accept").contains("application/json")
				|| (request.getHeader("X-Requested-With") != null && request.getHeader("X-Requested-With").contains("XMLHttpRequest")))){
			return false;
		}
		return true;
	}
	
	
	*//**
	 * 
	 * @Description 是否加密，单纯的认为手机端在有请求头为application/crypt-json时，返回结果需要加密
	 * @param request
	 * @return
	 *//*
	private boolean isJsonCrypt(HttpServletRequest request){
		if(request.getHeader("Content-Type").contains("application/crypt-json")) {
			return true;
		}
		return false;
	}
	
	*//**
	 * 
	 * @Description 头中包含appTag表示来自于非web端
	 * @param request
	 * @return
	 *//*
	private boolean isFromApp(HttpServletRequest request){
		if(StringUtils.isNotBlank(request.getHeader(appTag))) {
			return true;
		}
		return false;
	}
	

	*//**
	 * 
	 * @Description 判断是否是jsonp形式的ajax请求,这里jsonp的参数名称默认为：callback
	 * @return
	 *//*
	private boolean isAjaxJsonp(HttpServletRequest request){
		if(StringUtils.isNotBlank(request.getParameter(callback))){
			return true;
		}
		return false;
	}

	*//**
	 * @Description 注入callback的名称
	 * @param callback the callback to set
	 *//*
	public void setCallback(String callback) {
		this.callback = callback;
	}

	*//**
	 * @Description TODO
	 * @return the appTag
	 *//*
	public String getAppTag() {
		return appTag;
	}

	*//**
	 * @Description TODO
	 * @param appTag the appTag to set
	 *//*
	public void setAppTag(String appTag) {
		this.appTag = appTag;
	}
	
	*//**
	 * 
	 * @Description 
	 * @param data
	 *//*
	private void sendResponseData(String data,HttpServletResponse response){
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8");
		PrintWriter out = null;
		try {
		    out = response.getWriter();
		    out.write(data);
		} catch (IOException e) {
		    e.printStackTrace();
		} finally {
		    if (out != null) {
		        out.close();
		    }
		}
	}
	
	
	*//**
	 * 
	 * @Description 
	 * @param data
	 *//*
	private void sendCryptResponseData(String data,HttpServletResponse response){
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/crypt-json; charset=utf-8");
		PrintWriter out = null;
		try {
		    out = response.getWriter();
		    out.write(data);
		} catch (IOException e) {
		    e.printStackTrace();
		} finally {
		    if (out != null) {
		        out.close();
		    }
		}
	}
}
*/