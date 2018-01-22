package com.ccl.base.config.security.md5;

import org.springframework.security.authentication.encoding.MessageDigestPasswordEncoder;

public class Md5PasswordEncoder extends MessageDigestPasswordEncoder {

	public Md5PasswordEncoder() {
		super("MD5");
	}
	
	@Override
	protected String mergePasswordAndSalt(String password, Object salt, boolean strict) {
		if (password == null) {
			password = "";
		}

		if (strict && (salt != null)) {
			if ((salt.toString().lastIndexOf("{") != -1)
					|| (salt.toString().lastIndexOf("}") != -1)) {
				throw new IllegalArgumentException("Cannot use { or } in salt.toString()");
			}
		}

		if ((salt == null) || "".equals(salt)) {
			return password;
		}
		else {
			return password + salt.toString();
		}
	}
	

}