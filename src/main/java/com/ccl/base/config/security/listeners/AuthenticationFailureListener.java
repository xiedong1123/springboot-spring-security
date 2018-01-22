package com.ccl.base.config.security.listeners;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

import com.ccl.base.enums.user.UserAccountLockedEnum;
import com.ccl.base.utils.Const;
import com.ccl.base.utils.redis.RedisUtils;
import com.ccl.core.dao.IUserDao;
import com.ccl.core.entity.User;


/**
 * 
 * @ClassName：AuthenticationFailureListener
 * @Description：(密码输入错)登录失败监听器
 * @Author：xiedong
 * @Date：2017年12月19日下午1:05:05
 * @version：1.0.0
 */
@Component  
public class AuthenticationFailureListener implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {
	 private static final Logger logger = LoggerFactory.getLogger(AuthenticationFailureListener.class);
	
	@Autowired
	private IUserDao userDao;
   
	@Override
    public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent e){  
    	logger.info("------------------登录失败---------------");
    	logger.info("-------------登录失败-信息 :---"+e.getException().getMessage()+"-------------------");
    	String username = (String)e.getAuthentication().getPrincipal();
    	try {
			User user = userDao.getByUsername(username);
			if(user != null){
				String value = RedisUtils.getValue(Const.USER_REDIS_LOCKED + username + "_" + user.getId());
				Integer num = StringUtils.isNotBlank(value) ? Integer.valueOf(value) + 1 : 1;
				//密码输入错误次数超过设置限制修改用户锁定状态
				if(num >= Const.PWD_INPUT_ERROR_LIMIT && UserAccountLockedEnum.UNLOCKED.getCode() == user.getIsAccountLocked()){
					user.setIsAccountLocked((byte) 1);
					userDao.update(user);
					RedisUtils.removeValue(Const.USER_REDIS_LOCKED + username + "_" + user.getId());
					//开启倒计时
					new Thread(new Runnable() {
						@Override
						public void run() {
							int residualLockTime = Const.PWD_LOCK_TIME;
							while (residualLockTime > 0 ) {
								try {
									Thread.sleep(1000*60);
								} catch (InterruptedException e) {
									e.printStackTrace();
								} 
								residualLockTime--;
								logger.info("-----------剩余锁定时间-----: "+residualLockTime + "  分钟");
								
							}
							if(residualLockTime == 0 && UserAccountLockedEnum.LOCKED.getCode() == user.getIsAccountLocked()){
								logger.info("---------剩余锁定时间结束----------");
								user.setIsAccountLocked((byte) 0);
								try {
									userDao.update(user);
									logger.info("------锁定时间结束---修改用户状态成功----------");
								} catch (Exception e) {
									e.printStackTrace();
									logger.info("------锁定时间结束---修改用户状态失败----------");
								}
							}
							
						}
					}).start();
				}else if(num < Const.PWD_INPUT_ERROR_LIMIT ){
					RedisUtils.cacheValue(Const.USER_REDIS_LOCKED + username + "_" + user.getId(), ""+num,Const.PWD_VALID_TIME);
				}
				
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
           
    }  
} 