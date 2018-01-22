package com.ccl.core.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ccl.base.service.impl.BaseServiceImpl;
import com.ccl.core.dao.IRoleDao;
import com.ccl.core.entity.Role;
import com.ccl.core.service.IRoleService;

/**
 * 
 * @ClassName：RoleServiceImpl
 * @Description：
 * @author：xiedong
 * @Date：2017年12月14日下午5:50:18
 * @version：1.0.0
 */
@Service
public class RoleServiceImpl extends BaseServiceImpl<Role> implements IRoleService {

    @Autowired
    private IRoleDao roleDao;
    /**
   	 * 
   	 *	方法名：save
   	 *  @param role 
   	 *	@return
   	 *	throws Exception
   	 *	返回类型：void
   	 *	说明：保存
   	 *	创建人：
   	 * 	创建日期：2016-5-24上午11:53:03
   	 *	修改人：
   	 *	修改日期：
   	 */
    @Override
    @Transactional
    public void save(Role role) throws Exception {
    	role.setCreateTime(new Date());
    	roleDao.insert(role);
    	saveRolePermission(role.getId(),role.getResourceIds());//保存关联数据
    	
    }
    
    /**
   	 * 
   	 *	方法名：update
   	 *  @param role 
   	 *	@return
   	 *	throws Exception
   	 *	返回类型：void
   	 *	说明：修改
   	 *	创建人：
   	 * 	创建日期：2016-5-24上午11:53:03
   	 *	修改人：
   	 *	修改日期：
   	 */
    @Override
    @Transactional
    public void update(Role role) throws Exception {
    	roleDao.deleteRoleAndResource(role.getId());        //删除关联信息
    	saveRolePermission(role.getId(),role.getResourceIds());//保存关联数据
    	super.update(role);
    }

    /**
     * 
     * @MethodName：saveRolePermission
     * @param roleId
     * @param resourceIds
     * @throws Exception
     * @ReturnType：void
     * @Description：保存角色权限
     * @Creator：zhengjing
     * @CreateTime：2017年5月24日下午7:29:39
     * @Modifier：
     * @ModifyTime：
     */
	public void saveRolePermission(Long roleId, String resourceIds) throws Exception {
		if(roleId != null && resourceIds.length() > 0){
			String [] resIds = resourceIds.split(",");
			List<Map<String,Long>> listMap = new ArrayList<>();
			for (String resourceId : resIds) {
				Map<String, Long> map = new HashMap<>();
				map.put("roleId", roleId);
				map.put("resourceId", Long.valueOf(resourceId));
				listMap.add(map);
			}
			roleDao.insertRoleAndResource(listMap);        //添加关联信息
		}
	}
    /**
     * 
     * @MethodName：getByResourceId
     * @param resourceId
     * @return
     * @ReturnType：List<Role>
     * @Description：根据资源获取角色对象
     * @Creator：xiedong
     * @CreateTime：2017年5月24日下午7:50:09
     * @Modifier：
     * @ModifyTime：
     */
	@Override
	public List<Role> getByResourceId(Long resourceId) {
		return roleDao.getByResourceId(resourceId);
	}

	@Override
	public boolean isExists(HashMap<String, Object> params) throws Exception {
		return  roleDao.isExists(params) != 0;
	}

}
