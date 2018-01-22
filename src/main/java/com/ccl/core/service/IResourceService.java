package com.ccl.core.service;

import java.util.List;

import com.ccl.base.service.IBaseService;
import com.ccl.core.entity.Resource;
import com.ccl.core.entity.User;

/**
 * 
 * @ClassName：IResourceService
 * @Description：
 * @author：xiedong
 * @Date：2017年12月14日下午5:50:47
 * @version：1.0.0
 */
public interface IResourceService extends IBaseService<Resource> {
	// 根据角色Id获取权限资源
	public List<Resource> getPermissionsByRoleIds(String roleids) throws Exception;

	// 获取资源的tree列表
	public List<Resource> getTreelist() throws Exception;

	// 获取资源的tree列表
	public void getTreeList(List<Resource> returnList, Long l, Boolean flag) throws Exception;

	// 获取用户所拥有的功能按钮
	public List<Resource> getByUser(Long resId, User user) throws Exception;

	public List<Resource> getMenus(User user)throws Exception;

}
