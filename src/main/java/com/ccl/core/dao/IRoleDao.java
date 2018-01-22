package com.ccl.core.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ccl.base.dao.IBaseDao;
import com.ccl.core.entity.Role;

/**
 * 
 * @ClassName：IRoleDao
 * @Description：
 * @author：xiedong
 * @Date：2017年12月14日下午5:49:15
 * @version：1.0.0
 */
public interface IRoleDao extends IBaseDao<Role>{
		//保存角色资源
		void insertRoleAndResource(List<Map<String, Long>> list)throws Exception;
		//删除角色资源
		void deleteRoleAndResource(Long roleId)throws Exception;
		//根据资源获取角色对象
		List<Role> getByResourceId(Long resourceId);
		Integer isExists(HashMap<String, Object> params)throws Exception;
}
