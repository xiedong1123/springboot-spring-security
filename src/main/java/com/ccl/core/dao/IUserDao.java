package com.ccl.core.dao;

import java.util.HashMap;
import java.util.List;

import com.ccl.base.dao.IBaseDao;
import com.ccl.core.entity.User;

/**
 * 
 * @ClassName：IUserDao
 * @Description：
 * @author：xiedong
 * @Date：2017年12月14日下午5:49:10
 * @version：1.0.0
 */
public interface IUserDao  extends IBaseDao<User>{

	//根据用户名查询对象
    User getByUsername(String username)throws Exception;
    
	
	/**
	 * 判断账户是否重复
	 * @param account
	 * @param id
	 * @return
	 */
	Integer judgeExistOfUser(String username);
	
	/**
	 * 通过名字获取spring security所需的用户认证信息
	 * @param username
	 * @return
	 */
	User getByUsernameForSecurity(String username);
	/**
	 * 
	 * @MethodName：saveUserAndRole
	 * @param list
	 * @throws Exception
	 * @ReturnType：void
	 * @Description：保存中间表数据
	 * @Creator：xiedong
	 * @CreateTime：2017年12月12日下午4:34:20
	 * @Modifier：
	 * @ModifyTime：
	 */
	void insertUserAndRole(List<HashMap<String, Object>> list)throws Exception;
	/**
	 * 
	 * @MethodName：deleteUserAndRole
	 * @param id
	 * @throws Exception
	 * @ReturnType：void
	 * @Description：删除中间表数据
	 * @Creator：xiedong
	 * @CreateTime：2017年12月12日下午4:41:09
	 * @Modifier：
	 * @ModifyTime：
	 */
	void deleteUserAndRole(Long id)throws Exception;
}
