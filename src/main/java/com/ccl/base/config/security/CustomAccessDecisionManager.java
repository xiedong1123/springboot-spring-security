package com.ccl.base.config.security;

import java.util.Collection;
import java.util.Iterator;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

/**
 * 
 * @ClassName：CustomAccessDecisionManager
 * @Description：这个类主要由自己实现权限的认证
 * @Author：xiedong
 * @Date：2018年1月5日上午11:27:42
 * @version：1.0.0
 */
@Service
public class CustomAccessDecisionManager implements AccessDecisionManager {

	/**
	 * decide 方法是判定是否拥有权限的决策方法， authentication 是释CustomUserService中循环添加到
	 * GrantedAuthority 对象中的权限信息集合. object 包含客户端发起的请求的requset信息，可转换为
	 * HttpServletRequest request = ((FilterInvocation)
	 * object).getHttpRequest(); configAttributes
	 * 为CustomInvocationSecurityMetadataSource的getAttributes(Object
	 * object)这个方法返回的结果，此方法是为了判定用户请求的url 是否在权限表中，如果在权限表中，则返回给 decide
	 * 方法，用来判定用户是否有此权限。如果不在权限表中则放行。
	 */
	@Override
	public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes)
			throws AccessDeniedException, InsufficientAuthenticationException {

		if (null == configAttributes || configAttributes.size() <= 0) {
			return;
		}

		/*
		 * configAttributes.iterator().forEachRemaining(iter -> { String
		 * needRole = iter.getAttribute();
		 * authentication.getAuthorities().forEach(ga -> { if
		 * (needRole.trim().equals(ga.getAuthority())) { return; } }); });
		 */

		ConfigAttribute c;
		String needRole;
		for (Iterator<ConfigAttribute> iter = configAttributes.iterator(); iter.hasNext();) {
			c = iter.next();
			needRole = c.getAttribute();
			// authentication 为在注释1 中循环添加到 GrantedAuthority 对象中的权限信息集合
			for (GrantedAuthority ga : authentication.getAuthorities()) {
				if (needRole.trim().equals(ga.getAuthority())) {
					return;
				}
			}
		}
		throw new AccessDeniedException("no right");
	}

	@Override
	public boolean supports(ConfigAttribute attribute) {
		return true;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return true;
	}
}