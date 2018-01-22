/**
 ***********************************************************************
 * All rights Reserved, Designed By changhong
 * @Title: XSSFilter.java   
 * @Package com.changhong.framework.common.utils.filter   
 * @Description: TODO   
 * @author: liuchengyong     
 * @date: 2016-12-7 下午2:46:41   
 * @version V1.0 
 * @Copyright: 2016 www.changhong.com Inc. All rights reserved. 
 ***********************************************************************
 */
package com.ccl.base.utils.security.xss;

/**
 * @ClassName XSSFilter
 * @Description TODO
 * @author liuchengyong
 * @Date 2016-12-7 下午2:46:41
 * @version 1.0.0
 */
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class XSSFilter implements Filter {

	FilterConfig filterConfig = null;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

		this.filterConfig = filterConfig;

	}

	@Override
	public void destroy() {

		this.filterConfig = null;

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,

	FilterChain chain) throws IOException, ServletException {

		chain.doFilter(new XSSHttpServletRequestWrapper(

		(HttpServletRequest) request), response);

	}

}
