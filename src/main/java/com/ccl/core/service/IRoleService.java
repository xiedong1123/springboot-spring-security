package com.ccl.core.service;

import java.util.HashMap;
import java.util.List;

import com.ccl.base.service.IBaseService;
import com.ccl.core.entity.Role;

/**
 * 
 * @ClassName：IRoleService
 * @Description：
 * @author：xiedong
 * @Date：2017年12月14日下午5:50:53
 * @version：1.0.0
 */
public interface IRoleService extends IBaseService<Role>{
	//根据资源获取角色对象
	List<Role> getByResourceId(Long resourceId);
	boolean isExists(HashMap<String, Object> params)throws Exception;
}
