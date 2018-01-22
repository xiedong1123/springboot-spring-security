package com.ccl.base.config.security.listeners;

import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

import com.ccl.base.config.security.CustomerUserDetails;
import com.ccl.base.utils.Const;
import com.ccl.base.utils.redis.RedisUtils;


/**
 * 
 * @ClassName：AuthenticationSuccessEventListener
 * @Description：登录成功监听器
 * @Author：xiedong
 * @Date：2017年12月19日下午1:04:49
 * @version：1.0.0
 */
@Component  
public class AuthenticationSuccessEventListener implements ApplicationListener<AuthenticationSuccessEvent> {  
	
	
    public void onApplicationEvent(AuthenticationSuccessEvent e) {  
        CustomerUserDetails details =  (CustomerUserDetails)e.getAuthentication().getPrincipal();
        RedisUtils.removeValue(Const.USER_REDIS_LOCKED + details.getUsername() + "_" + details.getId());
           
    }  
} 
