/**
 ***********************************************************************
 * All rights Reserved, Designed By changhong
 * @Title: CSRFTokenManager.java   
 * @Package com.changhong.oto.common.utils.security.crsf   
 * @Description: TODO   
 * @author: liuchengyong     
 * @date: 2016-12-13 下午5:57:38   
 * @version V1.0 
 * @Copyright: 2016 www.changhong.com Inc. All rights reserved. 
 ***********************************************************************
 */
package com.ccl.base.utils.security.crsf;

import java.util.UUID;

import javax.servlet.http.HttpSession;

/**
 * @ClassName CSRFTokenManager
 * @Description TODO
 * @author liuchengyong
 * @Date 2016-12-13 下午5:57:38
 * @version 1.0.0
 */
public final class CSRFTokenManager {
	
	 // 隐藏域参数名称
    public static final String CSRF_TOKEN_FOR_SESSION_ATTR_NAME = "CSRFToken";
 
    private CSRFTokenManager() {
    };
 
    // 在session中创建csrfToken
    public static String createTokenForSession(HttpSession session) {
        String token = null;
        synchronized (session) {
            token = (String) session
                    .getAttribute(CSRF_TOKEN_FOR_SESSION_ATTR_NAME);
            if (null == token) {
                token = UUID.randomUUID().toString();
                session.setAttribute(CSRF_TOKEN_FOR_SESSION_ATTR_NAME, token);
            }
        }
        return token;
    }
}
