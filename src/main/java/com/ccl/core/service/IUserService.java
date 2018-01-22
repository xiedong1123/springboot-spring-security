package com.ccl.core.service;

import com.ccl.base.service.IBaseService;
import com.ccl.core.entity.User;

/**
 * 
 * @ClassName：IUserService
 * @Description：
 * @Author：xiedong
 * @Date：2017年12月12日下午3:11:59
 * @version：1.0.0
 */
public interface IUserService  extends IBaseService<User>{
	
	public void updatePass(User user)throws Exception;
	
	/**
	 * 判断一个账户名是否重复
	 * @param account
	 * @param id
	 * @return
	 */
	boolean judgeExistOfUser(String username);

	public void saveUser(User admin)throws Exception;

	public void updateStruts(Long id, Byte isEnabled) throws Exception;
	
}
