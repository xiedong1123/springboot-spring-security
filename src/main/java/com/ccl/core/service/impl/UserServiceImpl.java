package com.ccl.core.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ccl.base.config.security.md5.Md5PasswordEncoder;
import com.ccl.base.service.impl.BaseServiceImpl;
import com.ccl.core.dao.IUserDao;
import com.ccl.core.entity.User;
import com.ccl.core.service.IUserService;

/**
 * 
 * @ClassName：UserServiceImpl
 * @Description：
 * @author：xiedong
 * @Date：2017年12月14日下午5:50:24
 * @version：1.0.0
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<User> implements IUserService {

	@Autowired
	private IUserDao userDao;

	@Override
	@Transactional
	public void saveUser(User user) throws Exception {
		user.setCreateTime(new Date());
		user.setUpdateTime(new Date());
		user.setPassword(new Md5PasswordEncoder().encodePassword("123456", user.getUsername()));
		userDao.insert(user);
		//保存关联数据
		List<HashMap<String,Object>> list =  new ArrayList<HashMap<String,Object>>();
		for (String roleId : user.getRoleIds().split(",")) {
			HashMap<String,Object> map = new HashMap<>();
			map.put("userId", user.getId());
			map.put("roleId", roleId);
			list.add(map);
		}
		userDao.insertUserAndRole(list);
	}

	// 修改
	@Override
	@Transactional
	public void update(User user) throws Exception {
		user.setUpdateTime(new Date());
		userDao.update(user);
		userDao.deleteUserAndRole(user.getId());
		//保存关联数据
		List<HashMap<String,Object>> list =  new ArrayList<HashMap<String,Object>>();
		for (String roleId : user.getRoleIds().split(",")) {
			HashMap<String,Object> map = new HashMap<>();
			map.put("userId", user.getId());
			map.put("roleId", roleId);
			list.add(map);
		}
		userDao.insertUserAndRole(list);
	}

	// 修改状态
	@Override
	public void updateStruts(Long id, Byte isEnabled) throws Exception {
		User user = new User();
		user.setId(id);
		user.setIsEnabled(isEnabled);
		user.setUpdateTime(new Date());
		userDao.update(user);
	}

	@Override
	public void updatePass(User user) throws Exception {
		user.setUpdateTime(new Date());
		// user = PasswordHelper.encryptPassword(user);
		userDao.update(user);
	}


	@Override
	public boolean judgeExistOfUser(String username) {
		return userDao.judgeExistOfUser(username) != 0;
	}

}
