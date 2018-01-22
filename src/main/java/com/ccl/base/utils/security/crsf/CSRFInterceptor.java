/**
 ***********************************************************************
 * All rights Reserved, Designed By changhong
 * @Title: CSRFInterceptor.java   
 * @Package com.changhong.oto.common.utils.security.crsf   
 * @Description: TODO   
 * @author: liuchengyong     
 * @date: 2016-12-13 下午6:07:18   
 * @version V1.0 
 * @Copyright: 2016 www.changhong.com Inc. All rights reserved. 
 ***********************************************************************
 */
package com.ccl.base.utils.security.crsf;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.ccl.base.utils.O2oExcpetion;


/**
 * @ClassName CSRFInterceptor
 * @Description TODO
 * @author liuchengyong
 * @Date 2016-12-14 上午10:15:46
 * @version 1.0.0
 */
public class CSRFInterceptor extends HandlerInterceptorAdapter{
	private static final Logger logger = LoggerFactory
            .getLogger(CSRFInterceptor.class);
	
	private String tokenName;
  
    @Override
    public boolean preHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler) throws Exception {
    	//用户完成登录后，放入session或者调用shiro验证方法时放入
    	//按照http的规范，get请求不能用于资源的更新，所有资源更新操作均需要采用post请求
        if ("POST".equalsIgnoreCase(request.getMethod())) {
            String CsrfToken = this.getTokenFromRequest(request);
            if (CsrfToken == null
                    || !CsrfToken.equals(request.getSession().getAttribute(
                    		CSRFTokenManager.CSRF_TOKEN_FOR_SESSION_ATTR_NAME))) {
            	logger.info("请求异常，可能原因由于crsf攻击造成");
                throw new O2oExcpetion("请求异常，可能原因由于crsf攻击造成", -1);
            }
        }
        return true;
    }
    
    /**
     * 
     * @Description 获取request中的token值
     * @param request
     * @return
     */
    private String getTokenFromRequest(HttpServletRequest request){
    	return request.getParameter(tokenName);
    }
}
