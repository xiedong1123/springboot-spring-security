package com.ccl.base.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.ccl.base.config.security.md5.Md5PasswordEncoder;

/**
 * 
 * @ClassName：MyAuthenticationProvider
 * @Description：自定义验证规则
 * @Author：xiedong
 * @Date：2017年9月20日上午11:26:14
 * @version：1.0.0
 */
@Component
public class MyAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		
//		UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;   
		MyAuthenticationToken token = (MyAuthenticationToken) authentication;  
		Object validationCode = token.getValidationCode();
        String username = token.getName(); 
        
        //从数据库找到的用户  
        CustomerUserDetails userDetails = null;  
        if(username != null) {  
            userDetails = (CustomerUserDetails)customUserDetailsService.loadUserByUsername(username);  
        }  
        //  
        if(userDetails == null) {  
            throw new UsernameNotFoundException("用户名/密码无效");  
        }else if (userDetails.isEnabled()){  
            throw new DisabledException("用户已被禁用");  
        }else if (userDetails.isAccountNonLocked()) { 
            throw new LockedException("账户已被锁定");  
        }
        
        //数据库用户的密码  
        String password = userDetails.getPassword();  
        //与authentication里面的credentials相比较  
        Md5PasswordEncoder md5PasswordEncoder = new Md5PasswordEncoder();
        String encodePassword = md5PasswordEncoder.encodePassword(token.getCredentials().toString(), userDetails.getSalt());
        if(!password.equals(encodePassword)) {  
            throw new BadCredentialsException("用户名/密码无效");  
        }  
			//授权  
//	        return new UsernamePasswordAuthenticationToken(userDetails, password,userDetails.getAuthorities()); 
		return new MyAuthenticationToken(userDetails, password,userDetails.getAuthorities(),validationCode);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return true;
	}

}
