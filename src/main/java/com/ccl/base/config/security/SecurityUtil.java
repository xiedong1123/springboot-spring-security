package com.ccl.base.config.security;

import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {
	
	public static final String INIT_PASSWORD  = "123";
	
	public static CustomerUserDetails getLoginUser() {
		return (CustomerUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}
	
	public static void refreshUserInSession() {
	}
}
