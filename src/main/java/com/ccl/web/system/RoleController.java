package com.ccl.web.system;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.ccl.base.utils.AjaxResult;
import com.ccl.base.utils.Const;
import com.ccl.base.utils.LogUtils;
import com.ccl.base.utils.page.PageResult;
import com.ccl.core.entity.Resource;
import com.ccl.core.entity.Role;
import com.ccl.core.query.RoleQuery;
import com.ccl.core.service.IResourceService;
import com.ccl.core.service.IRoleService;
import com.ccl.web.base.BaseController;

/**
 * 
 * @ClassName：RoleController @Description：
 * @author：xiedong
 * @Date：2017年12月14日上午9:26:27 @version：1.0.0
 */
@Controller
@RequestMapping("/role")
public class RoleController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(RoleController.class);
	@Autowired
	private IRoleService roleService;
	@Autowired
	IResourceService resourceService;

	/**
	 * 
	 * @MethodName：listUI
	 * @return
	 * @throws Exception
	 * @ReturnType：String
	 * @Description：列表UI
	 * @Creator：xiedong
	 * @CreateTime：2017年5月24日下午10:58:09 @Modifier： @ModifyTime：
	 */
	@RequestMapping("/listUI")
	@Override
	public String listUI(Model model) throws Exception {
		LogUtils.info(logger, "角色列表进入，入参{}", model);
		return Const.SYSTEM + "/role/list";
	}

	/**
	 * 
	 * @MethodName：pageList
	 * @param roleQuery
	 * @return
	 * @throws Exception
	 * @ReturnType：PageResult<Role>
	 * @Description：分页查询
	 * @Creator：xiedong
	 * @CreateTime：2017年12月14日上午9:26:47 @Modifier： @ModifyTime：
	 */
	@RequestMapping("/pagelist")
	@ResponseBody
	public PageResult<Role> pageList(RoleQuery roleQuery) throws Exception {
		LogUtils.info(logger, "角色查询进入，入参{}", roleQuery);
		PageResult<Role> roleList = null;
		try {
			roleList = roleService.findPage(roleQuery);
			LogUtils.info(logger, "角色查询成功");
		} catch (Exception e) {
			LogUtils.error(logger, "角色管理-查询失败", e);
			LogUtils.error(logger, "角色查询失败");
		}
		return roleList;
	}

	/**
	 * 
	 * @MethodName：addUI
	 * @param model
	 * @return
	 * @throws Exception
	 * @ReturnType：String
	 * @Description：添加页面跳转
	 * @Creator：xiedong
	 * @CreateTime：2017年12月14日上午9:26:58 @Modifier： @ModifyTime：
	 */
	@RequestMapping("/addUI")
	public String addUI(Model model) throws Exception {
		return Const.SYSTEM + "/role/add";
	}

	/**
	 * 
	 * @MethodName：add
	 * @param role
	 * @return
	 * @throws Exception
	 * @ReturnType：AjaxResult
	 * @Description：新增保存数据
	 * @Creator：xiedong
	 * @CreateTime：2017年12月14日上午9:27:09 @Modifier： @ModifyTime：
	 */
	@ResponseBody
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public AjaxResult add(Role role) throws Exception {
		LogUtils.info(logger, "角色添加进入，入参{}", role);
		AjaxResult ajaxResult = null;
		try {
			roleService.save(role);
			ajaxResult = new AjaxResult(true, "操作成功!");
			LogUtils.info(logger, "角色添加成功");
			LogUtils.writeOperateLog("角色管理", "添加" + role.getName() + "角色成功", role.getName());
		} catch (Exception e) {
			ajaxResult = new AjaxResult(false, "操作失败!");
			LogUtils.error(logger, "角色管理-添加角色出错", e);
			LogUtils.error(logger, "角色添加失败");
		}
		return ajaxResult;
	}

	/**
	 * 
	 * @MethodName：getDetails
	 * @param id
	 * @param model
	 * @return
	 * @throws Exception
	 * @ReturnType：String
	 * @Description：查看详情
	 * @Creator：xiedong
	 * @CreateTime：2017年12月14日上午9:27:27 @Modifier： @ModifyTime：
	 */
	@RequestMapping("/getDetails/{id}")
	public String getDetails(@PathVariable Long id, Model model) throws Exception {
		LogUtils.info(logger, "管理员详情列表进入，入参{}", id);
		Role role = roleService.getById(id);
		model.addAttribute("role", role);
		List<Resource> permissions = resourceService.getPermissionsByRoleIds(role.getId().toString());
		model.addAttribute("permissions", JSON.toJSON(permissions));
		return Const.SYSTEM + "/role/details";
	}

	/**
	 * 
	 * @MethodName：editUI
	 * @param id
	 * @param model
	 * @return
	 * @throws Exception
	 * @ReturnType：String
	 * @Description：编辑页面跳转
	 * @Creator：xiedong
	 * @CreateTime：2017年12月14日上午9:27:38 @Modifier： @ModifyTime：
	 */
	@RequestMapping("/editUI/{id}")
	public String editUI(@PathVariable Long id, Model model) throws Exception {
		Role role = roleService.getById(id);
		model.addAttribute("role", role);
		List<Resource> permissions = resourceService.getPermissionsByRoleIds(id+"");
		model.addAttribute("permissions", JSON.toJSON(permissions));
		return Const.SYSTEM + "/role/edit";
	}

	/**
	 * 
	 * @MethodName：edit
	 * @param role
	 * @return
	 * @throws Exception
	 * @ReturnType：AjaxResult
	 * @Description：编辑
	 * @Creator：xiedong
	 * @CreateTime：2017年12月14日上午9:27:51 @Modifier： @ModifyTime：
	 */
	@ResponseBody
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public AjaxResult edit(Role role) throws Exception {
		LogUtils.info(logger, "角色编辑进入，入参{}", role);
		AjaxResult ajaxResult = null;
		try {
			role.setUpdateTime(new Date());
			roleService.update(role);
			ajaxResult = new AjaxResult(true, "操作成功!");
			LogUtils.writeOperateLog("角色管理", "编辑" + role.getName() + "角色成功", role.getName());
			LogUtils.info(logger, "角色编辑成功");
		} catch (Exception e) {
			ajaxResult = new AjaxResult(false, "操作失败!");
			LogUtils.error(logger, "角色管理-编辑失败", e);
			LogUtils.error(logger, "角色编辑失败");
		}
		return ajaxResult;
	}

	/**
	 * 
	 * @MethodName：delete
	 * @param id
	 * @return
	 * @throws Exception
	 * @ReturnType：AjaxResult
	 * @Description：删除角色
	 * @Creator：xiedong
	 * @CreateTime：2017年12月14日上午9:27:58 @Modifier： @ModifyTime：
	 */
	@ResponseBody
	@RequestMapping(value = "/delete{id}")
	public AjaxResult delete(Long id) throws Exception {
		AjaxResult ajaxResult = null;
		LogUtils.info(logger, "角色删除进入，入参{}", id);
		try {
			roleService.deleteById(id);
			ajaxResult = new AjaxResult(true, "操作成功!");
			LogUtils.info(logger, "角色删除成功");
			LogUtils.writeOperateLog("角色管理", "角色删除成功", id);
		} catch (Exception e) {
			ajaxResult = new AjaxResult(false, "操作失败!");
			LogUtils.error(logger, "角色管理-删除失败", e);
			LogUtils.error(logger, "角色删除失败");
		}
		return ajaxResult;
	}

	/**
	 * 
	 * @MethodName：getRoleName
	 * @param key
	 * @param id
	 * @return
	 * @ReturnType：AjaxResult @Description：
	 * @Creator：xiedong
	 * @CreateTime：2017年12月14日上午11:10:25 @Modifier： @ModifyTime：
	 */
	@ResponseBody
	@RequestMapping("/isExistsKey")
	public Boolean isExistsKey(String key,Long id) {
		// true存在, false不存在
		boolean result = true;
		if (StringUtils.isBlank(key) ) {
			// 不允许空校验
			return !result;
		}
		try {
			if (id != null) {
				Role role = roleService.getById(id);
				if (key.equals(role.getKey())) {
					return result;
				}
			}
			HashMap<String, Object> params = new HashMap<String,Object>();
			params.put("key", key);
			result = roleService.isExists(params);

		} catch (Exception e) {
			e.printStackTrace();
			LogUtils.error(logger, e.getMessage(), e);
		}
		return !result;
	}
	@ResponseBody
	@RequestMapping("/isExistsName")
	public Boolean isExistsName(String name,Long id) {
		// true存在, false不存在
		boolean result = true;
		if (StringUtils.isBlank(name) ) {
			// 不允许空校验
			return !result;
		}
		try {
			if (id != null) {
				Role role = roleService.getById(id);
				if (name.equals(role.getName())) {
					return result;
				}
			}
			HashMap<String, Object> params = new HashMap<String,Object>();
			params.put("name", name);
			result = roleService.isExists(params);
			
		} catch (Exception e) {
			e.printStackTrace();
			LogUtils.error(logger, e.getMessage(), e);
		}
		return !result;
	}
}
