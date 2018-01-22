package com.ccl.base.config.security;

import org.springframework.security.core.AuthenticationException;

public class ValidationCodeException extends AuthenticationException{
	private static final long serialVersionUID = 8103087051684316665L;

	public ValidationCodeException(String msg) {
		super(msg);
	}
	
	public ValidationCodeException(String msg ,Throwable t) {
		super(msg, t);
	}
}
