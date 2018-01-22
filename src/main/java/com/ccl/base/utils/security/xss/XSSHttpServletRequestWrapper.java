/**
 ***********************************************************************
 * All rights Reserved, Designed By changhong
 * @Title: XSSHttpServletRequestWrapper.java   
 * @Package com.changhong.framework.common.utils.filter   
 * @Description: TODO   
 * @author: liuchengyong     
 * @date: 2016-12-7 下午2:47:54   
 * @version V1.0 
 * @Copyright: 2016 www.changhong.com Inc. All rights reserved. 
 ***********************************************************************
 */
package com.ccl.base.utils.security.xss;

/**
 * @ClassName XSSHttpServletRequestWrapper
 * @Description TODO
 * @author liuchengyong
 * @Date 2016-12-7 下午2:47:54
 * @version 1.0.0
 */

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.springframework.web.util.HtmlUtils;

public class XSSHttpServletRequestWrapper extends HttpServletRequestWrapper {

	public XSSHttpServletRequestWrapper(HttpServletRequest servletRequest) {
		super(servletRequest);
	}

	@Override
	public String[] getParameterValues(String parameter) {
		String[] values = super.getParameterValues(parameter);
		if (values == null) {
			return null;
		}
		int count = values.length;
		String[] encodedValues = new String[count];
		for (int i = 0; i < count; i++) {
			encodedValues[i] = cleanXSS(values[i]);
		}
		return encodedValues;

	}

	@Override
	public String getParameter(String parameter) {
		String value = super.getParameter(parameter);
		if (value == null) {
			return null;
		}
		return cleanXSS(value);
	}

	@Override
	public String getHeader(String name) {
		String value = super.getHeader(name);
		if (value == null) {
			return null;
		}
		return cleanXSS(value);
	}
	
	/**
	 * 
	 * @Description 清除xss，需要将字段转换回去
	 * @param value
	 * @return
	 */
	private String cleanXSS(String value) {
		
		//第一种
		/*value = value.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
		value = value.replaceAll("\\(", "&#40;").replaceAll("\\)", "&#41;");
		value = value.replaceAll("'", "&#39;");
		value = value.replaceAll("eval\\((.*)\\)", "");
		value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']",
				"\"\"");
		value = value.replaceAll("script", "");*/
		
		//第二种
		/*value = StringEscapeUtils.escapeHtml(value); 
		value = StringEscapeUtils.escapeJavaScript(value); */
		
		//第三种
		return HtmlUtils.htmlEscape(value);
	}
}
