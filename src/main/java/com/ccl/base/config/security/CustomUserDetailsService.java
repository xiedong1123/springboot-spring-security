package com.ccl.base.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ccl.core.dao.IUserDao;
import com.ccl.core.entity.User;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired // 数据库服务类
	private IUserDao userDao;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userDao.getByUsernameForSecurity(username);
		CustomerUserDetails customerUserDetails = null;
		if(user == null){
			throw new UsernameNotFoundException("用户名或密码错误");
		}
		/*String value = RedisUtils.getValue("user_redis_locked_" + username + "_" + user.getId());
		if(StringUtils.isBlank(value) && 1 == user.getIsAccountLocked()){
			try {
				user.setIsAccountLocked((byte)0);
				userDao.update(user);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}*/
		customerUserDetails = new CustomerUserDetails(user);
		return customerUserDetails;
	}
}