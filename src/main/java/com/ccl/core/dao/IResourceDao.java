package com.ccl.core.dao;

import java.util.HashMap;
import java.util.List;

import com.ccl.base.dao.IBaseDao;
import com.ccl.core.entity.Resource;


/**
 * 
 * @ClassName：IResourceDao
 * @Description：
 * @author：xiedong
 * @Date：2017年12月14日下午5:49:21
 * @version：1.0.0
 */
public interface IResourceDao extends IBaseDao<Resource>{
	//根据角色Id获取权限资源
	List<Resource> getPermissionsByRoleIds(HashMap<String,Object> parame) throws Exception;

	List<Resource> getByEmployeeWhere(HashMap<String, Object> params)throws Exception;
	
	//获取 Security 权限资源
	List<Resource> loadResourceDefine();

}
