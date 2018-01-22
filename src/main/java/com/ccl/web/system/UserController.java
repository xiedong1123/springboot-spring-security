package com.ccl.web.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import com.ccl.base.config.security.SecurityUtil;
import com.ccl.base.utils.AjaxResult;
import com.ccl.base.utils.Const;
import com.ccl.base.utils.LogUtils;
import com.ccl.base.utils.excel.ExcelDownLoadListParamBean;
import com.ccl.base.utils.excel.WriteExcelUtil;
import com.ccl.base.utils.page.PageResult;
import com.ccl.core.entity.Role;
import com.ccl.core.entity.User;
import com.ccl.core.query.UserQuery;
import com.ccl.core.service.IRoleService;
import com.ccl.core.service.IUserService;
import com.ccl.web.aop.Sign;
import com.ccl.web.base.BaseController;


/**
 * 
 * @ClassName：UserController
 * @Description：用户管理
 * @author：xiedong
 * @Date：2017年12月13日下午1:59:59
 * @version：1.0.0
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	@Autowired
	private IUserService userService;
	@Autowired
	private IRoleService roleService;
	
	@RequestMapping("/test")
	@ResponseBody
	public String test(){
		return "xxxxxx";
	}
	
	/**
	 * 列表
	 */
	@Override
	@RequestMapping("/listUI")
	public String listUI(Model model) throws Exception {
		LogUtils.info(logger, "管理员管理列表进入，入参{}", model);
		try {
			List<Role> roles = roleService.getAll();
			model.addAttribute("roles", roles);
		} catch (Exception e) {
			e.printStackTrace();
			LogUtils.error(logger, "管理员管理列表-获取城市列表失败");
		}
		return Const.SYSTEM + "/user/list";
	}


	/**
	 * 
	 * @MethodName：exportadmin
	 * @param userQuery
	 * @param request
	 * @param response
	 * @param fileName
	 * @throws Exception
	 * @ReturnType：void
	 * @Description：导出
	 * @Creator：xiedong
	 * @CreateTime：2017年12月13日下午1:38:51
	 * @Modifier：
	 * @ModifyTime：
	 */
	@RequestMapping("/export")
	public void exportadmin(UserQuery userQuery, HttpServletRequest request, HttpServletResponse response,
			String fileName) throws Exception {
		LogUtils.info(logger, "管理员管理导出进入，入参{}", userQuery);
		//List<SysUserVo> list = null;
		try {
			List<User> userList = userService.findPage(userQuery).getData();
			// "管理员管理列表"
			WriteExcelUtil.toExcel(userList, request, response, fileName);
			LogUtils.writeOperateLog("管理员管理", "管理员管理Excel导出");
			LogUtils.info(logger, "管理员导出成功");
		} catch (Exception e) {
			LogUtils.error(logger,"管理员管理-导出失败!服务器异常", e);
			LogUtils.error(logger, "管理员导出失败");
		}
	}

	/**
	 * 
	 * @MethodName：getCount
	 * @param userQuery
	 * @param count
	 * @return
	 * @ReturnType：AjaxResult
	 * @Description：导出获取条数
	 * @Creator：xiedong
	 * @CreateTime：2017年12月13日下午1:58:24
	 * @Modifier：
	 * @ModifyTime：
	 */
	@ResponseBody
	@RequestMapping("/getCount")
	public AjaxResult getCount(UserQuery userQuery) {
		AjaxResult ajaxResult = null;
		try {
			String fileName = "管理员管理列表";
			Integer totalCount = userService.findCount(userQuery);
			//配置文件设置
			Integer configExportCount = super.getConfigExportCount();
			List<ExcelDownLoadListParamBean> list = WriteExcelUtil.getExcelDownLoadListParam(fileName, totalCount, configExportCount);
			if (list != null && list.size() > 0) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("list", list);
				map.put("totle", totalCount);
				ajaxResult = new AjaxResult(true, "请求成功!", map);
				LogUtils.info(logger, "请求成功!");
			} else {
				ajaxResult = new AjaxResult(false, "无数据，请重新查询再导出。");
				LogUtils.info(logger, "请求失败 !");
			}
		} catch (Exception e) {
			e.printStackTrace();
			LogUtils.error(logger, e.getMessage(), e);
			ajaxResult = new AjaxResult(false, "请求失败 !");
		}
		return ajaxResult;
	}

	/**
	 * 
	 * @MethodName：pageList
	 * @param userQuery
	 * @return
	 * @throws Exception
	 * @ReturnType：PageResult<User>
	 * @Description：分页数据
	 * @Creator：xiedong
	 * @CreateTime：2017年12月13日下午2:00:19
	 * @Modifier：
	 * @ModifyTime：
	 */
	@RequestMapping("/pagelist")
	@ResponseBody
	@Sign
	public PageResult<User> pageList(UserQuery userQuery) throws Exception {
		LogUtils.info(logger, "管理员管理查询进入，入参{}", userQuery);
		PageResult<User> adminList = null;
		try {
			adminList = userService.findPage(userQuery);
			LogUtils.info(logger, "管理员查询成功");
		} catch (Exception e) {
			e.printStackTrace();
			LogUtils.error(logger,"管理员管理-查询失败", e);
			LogUtils.error(logger, "管理员查询失败");
		}
		return adminList;
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
	 * @CreateTime：2017年12月13日下午2:01:40
	 * @Modifier：
	 * @ModifyTime：
	 */
	@RequestMapping("/editUI/{id}")
	public String editUI(@PathVariable("id") Long id, Model model) throws Exception {
		User user = userService.getById(id);
		model.addAttribute("user", user);
		List<Role> roleAlls = roleService.getAll();
		model.addAttribute("roleAlls", roleAlls);
		return Const.SYSTEM + "/user/edit";
	}
	
	/**
	 * 
	 * @MethodName：edit
	 * @param sysuser
	 * @return
	 * @throws Exception
	 * @ReturnType：AjaxResult
	 * @Description：修改用户信息
	 * @Creator：xiedong
	 * @CreateTime：2017年12月13日下午2:00:47
	 * @Modifier：
	 * @ModifyTime：
	 */
	@ResponseBody
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public AjaxResult edit(User user) throws Exception {
		LogUtils.info(logger, "管理员管理编辑进入，入参{}", user);
		AjaxResult ajaxResult = null;
		try {
			User u = SecurityUtil.getLoginUser();
			userService.update(user);
			LogUtils.writeOperateLog("管理员管理", u.getUsername() + "编辑管理员" + user.getUsername());
			ajaxResult = new AjaxResult(true, "操作成功!");
			LogUtils.info(logger, "管理员编辑成功");
		} catch (Exception e) {
			e.printStackTrace();
			ajaxResult = new AjaxResult(false, "操作失败!");
			LogUtils.error(logger,"管理员管理-编辑失败", e);
			LogUtils.error(logger, "管理员编辑失败");
		}
		return ajaxResult;
	}

	/**
	 * 
	 * @MethodName：updateStatusUI
	 * @param id
	 * @param isEnabled
	 * @param model
	 * @return
	 * @throws Exception
	 * @ReturnType：String
	 * @Description：修改用户状态
	 * @Creator：xiedong
	 * @CreateTime：2017年12月13日上午10:18:40
	 * @Modifier：
	 * @ModifyTime：
	 */
	@RequestMapping("/updateStatusUI")
	public String updateStatusUI(Long id, Byte isEnabled, Model model) throws Exception {
		model.addAttribute("id", id);
		model.addAttribute("isEnabled", isEnabled);
		return Const.SYSTEM + "/user/userStatus";
	}

	/**
	 * 
	 * @MethodName：updateStstus
	 * @param id
	 * @param isEnabled
	 * @return
	 * @throws Exception
	 * @ReturnType：AjaxResult
	 * @Description：修改状态
	 * @Creator：xiedong
	 * @CreateTime：2017年12月13日下午2:01:04
	 * @Modifier：
	 * @ModifyTime：
	 */
	@ResponseBody
	@RequestMapping(value = "/updateStstus")
	public AjaxResult updateStstus(Long id, Byte isEnabled) throws Exception {
		LogUtils.info(logger, "管理员管理编辑状态进入，入参{}", id, isEnabled);
		AjaxResult ajaxResult = null;
		try {
			userService.updateStruts(id,isEnabled);
			ajaxResult = new AjaxResult(true, "修改成功");
			LogUtils.info(logger, "管理员编辑状态成功");
		} catch (Exception e) {
			e.printStackTrace();
			ajaxResult = new AjaxResult(false, "操作失败!");
			LogUtils.error(logger,"管理员管理-修改管理员启用/禁用出错", e);
			LogUtils.error(logger, "管理员编辑状态失败");
		}
		return ajaxResult;
	}
	

	/**
	 * @Description 跳转退出确认页面
	 * @param id
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/loginOutUI")
	public String loginOutUI(Model model) throws Exception {
		return Const.SYSTEM + "/loginOut";
	}
	

	/**
	 * 
	 * @MethodName：addUI
	 * @param model
	 * @return
	 * @throws Exception
	 * @ReturnType：String
	 * @Description：新增页面跳转
	 * @Creator：xiedong
	 * @CreateTime：2017年12月13日下午2:02:17
	 * @Modifier：
	 * @ModifyTime：
	 */
	@RequestMapping("/addUI")
	public String addUI(Model model) throws Exception {
		List<Role> roleAlls = roleService.getAll();
		model.addAttribute("roleAlls", roleAlls);
		return Const.SYSTEM + "/user/add";
	}

	/**
	 * 
	 * @MethodName：add
	 * @param admin
	 * @return
	 * @throws Exception
	 * @ReturnType：AjaxResult
	 * @Description：新增用户
	 * @Creator：xiedong
	 * @CreateTime：2017年12月13日下午2:02:29
	 * @Modifier：
	 * @ModifyTime：
	 */
	@ResponseBody
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public AjaxResult add(User user) throws Exception {
		LogUtils.info(logger, "管理员添加用户进入，入参{}", user);
		AjaxResult ajaxResult = null;
		try {
			//当前登录用户
			User u = SecurityUtil.getLoginUser();
			userService.saveUser(user);
			LogUtils.writeOperateLog("管理员管理", u.getUsername() + "添加" + user.getUsername() + "管理员");
			ajaxResult = new AjaxResult(true, "操作成功!");
			LogUtils.info(logger, "管理员添加用户成功");
		} catch (Exception e) {
			e.printStackTrace();
			ajaxResult = new AjaxResult(false, "操作失败!");
			LogUtils.error(logger,"管理员管理-添加系统管理员出错", e);
			LogUtils.error(logger, "管理员添加用户失败");
		}
		return ajaxResult;
	}

	/**
	 * 
	 * @MethodName：getDetails
	 * @param id
	 * @param model
	 * @return
	 * @ReturnType：String
	 * @Description：详情查看
	 * @Creator：xiedong
	 * @CreateTime：2017年12月13日下午2:02:43
	 * @Modifier：
	 * @ModifyTime：
	 */
	@RequestMapping("/getDetails")
	public String getDetails(Long id, Model model) {
		LogUtils.info(logger, "管理员详情列表进入，入参{}", id);
		// PasswordHelper
		try {

			model.addAttribute("user", userService.getById(id));
			List<Role> roleAlls = roleService.getAll();
			model.addAttribute("roleAlls", roleAlls);
			LogUtils.info(logger, "管理员详情查询列表成功");
		} catch (Exception e) {
			e.printStackTrace();
			LogUtils.error(logger,"管理员管理-查询管理员详情页面数据出错", e);
			LogUtils.error(logger, "管理员详情查询列表失败");
		}
		return Const.SYSTEM + "/user/details";
	}

	/**
	 * 
	 * @MethodName：getAccount
	 * @param username
	 * @param id
	 * @return
	 * @ReturnType：boolean
	 * @Description：判断用户登录账号是否存在
	 * @Creator：xiedong
	 * @CreateTime：2017年12月13日下午2:02:54
	 * @Modifier：
	 * @ModifyTime：
	 */
	@ResponseBody
	@RequestMapping("/getAccount")
	public boolean getAccount(String username,Long id) {
		//true存在, false不存在
		boolean result = true;
		if(StringUtils.isBlank(username)){
			//不允许空校验
			return !result;
		}
		try {
			if(id != null) {
				User user = userService.getById(id);
				if(username.equals(user.getUsername())) {
					return result;
				}
			}
			
			result = userService.judgeExistOfUser(username);
			
		} catch (Exception e) {
			e.printStackTrace();
			LogUtils.error(logger,e.getMessage(), e);
		}
		return !result;
	}
}
