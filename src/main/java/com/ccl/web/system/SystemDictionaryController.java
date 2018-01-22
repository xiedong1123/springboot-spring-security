package com.ccl.web.system;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ccl.base.config.security.SecurityUtil;
import com.ccl.base.utils.AjaxResult;
import com.ccl.base.utils.Const;
import com.ccl.base.utils.LogUtils;
import com.ccl.base.utils.page.PageResult;
import com.ccl.core.entity.SystemDictionary;
import com.ccl.core.entity.User;
import com.ccl.core.query.SystemDictionaryQuery;
import com.ccl.core.service.ISystemDictionaryService;
import com.ccl.web.base.BaseController;


/**
 * 
 * @ClassName：SystemDictionaryController
 * @Description：
 * @Author：xiedong
 * @Date：2017年12月20日下午4:28:54
 * @version：1.0.0
 */
@Controller
@RequestMapping("/systemDictionary")
public class SystemDictionaryController extends BaseController{
	private static final Logger logger = LoggerFactory.getLogger(SystemDictionaryController.class);
	
	@Autowired
	private ISystemDictionaryService systemDictionaryService;
	
	
	@Override
	@RequestMapping("/listUI")
	protected String listUI(Model model) throws Exception {
		return Const.SYSTEM + "/systemDictionary/list";
	}
	/**
	 * 
	 * @MethodName：pageList
	 * @param systemDictionaryQuery
	 * @return
	 * @ReturnType：PageResult<SystemDictionary>
	 * @Description：
	 * @Creator：xiedong
	 * @CreateTime：2017年12月20日下午4:28:48
	 * @Modifier：
	 * @ModifyTime：
	 */
	@RequestMapping("/pageList")
	@ResponseBody
	public PageResult<SystemDictionary> pageList(SystemDictionaryQuery systemDictionaryQuery){
		PageResult<SystemDictionary> findPage = null;
		try {
			findPage = systemDictionaryService.findPage(systemDictionaryQuery);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return findPage;
	}
	/**
	 * 
	 * @MethodName：detailUI
	 * @param code
	 * @param model
	 * @return
	 * @ReturnType：String
	 * @Description：
	 * @Creator：xiedong
	 * @CreateTime：2017年12月20日下午4:28:45
	 * @Modifier：
	 * @ModifyTime：
	 */
	@RequestMapping("/detailUI/{code}")
	public String detailUI(@PathVariable("code")String code,Model model) {
		model.addAttribute("code", code);
		return Const.SYSTEM + "/systemDictionary/detail";
	}
	/**
	 * 
	 * @MethodName：addUI
	 * @return
	 * @ReturnType：String
	 * @Description：
	 * @Creator：xiedong
	 * @CreateTime：2017年12月20日下午4:28:39
	 * @Modifier：
	 * @ModifyTime：
	 */
	@RequestMapping("/addUI")
	public String addUI() {
		return Const.SYSTEM + "/systemDictionary/add";
	}
	/**
	 * 
	 * @MethodName：add
	 * @param systemDictionary
	 * @return
	 * @ReturnType：AjaxResult
	 * @Description：
	 * @Creator：xiedong
	 * @CreateTime：2017年12月20日下午4:28:35
	 * @Modifier：
	 * @ModifyTime：
	 */
	@RequestMapping("/add")
	@ResponseBody
	public AjaxResult add(SystemDictionary systemDictionary) {
		LogUtils.info(logger,"保存入口systemDictionary{}",systemDictionary);
		AjaxResult ajaxResult = null;
		try {
			systemDictionary.setCreateTime(new Date());
			systemDictionaryService.save(systemDictionary);
			ajaxResult = new AjaxResult(true, "修改成功");
			User user = SecurityUtil.getLoginUser();
			LogUtils.writeOperateLog("数据字典", "{}账号:{},新增code为:[{}]的字典", user.getName(),user.getUsername(),systemDictionary.getCode());
		} catch (Exception e) {
			e.printStackTrace();
			ajaxResult = new AjaxResult(false, "修改失败");
		}
		return ajaxResult;
	}
	/**
	 * 
	 * @MethodName：editUI
	 * @param id
	 * @param model
	 * @return
	 * @ReturnType：String
	 * @Description：
	 * @Creator：xiedong
	 * @CreateTime：2017年12月20日下午4:28:30
	 * @Modifier：
	 * @ModifyTime：
	 */
	@RequestMapping("/editUI/{id}")
	public String editUI(@PathVariable("id")Long id,Model model) {
		try {
			SystemDictionary systemDictionary = systemDictionaryService.getById(id);
			model.addAttribute(systemDictionary);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Const.SYSTEM + "/systemDictionary/edit";
	}
	/**
	 * 
	 * @MethodName：edit
	 * @param systemDictionary
	 * @return
	 * @ReturnType：AjaxResult
	 * @Description：
	 * @Creator：xiedong
	 * @CreateTime：2017年12月20日下午4:28:24
	 * @Modifier：
	 * @ModifyTime：
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public AjaxResult edit(SystemDictionary systemDictionary) {
		LogUtils.info(logger,"编辑入口systemDictionary{}",systemDictionary);
		AjaxResult ajaxResult = null;
		try {
			systemDictionary.setUpdateTime(new Date());
			systemDictionaryService.update(systemDictionary);
			ajaxResult = new AjaxResult(true, "修改成功");
			User user = SecurityUtil.getLoginUser();
			LogUtils.writeOperateLog("数据字典", "{}账号:{},编辑code为:[{}]的字典", user.getName(),user.getUsername(),systemDictionary.getCode());
		} catch (Exception e) {
			e.printStackTrace();
			ajaxResult = new AjaxResult(false, "修改失败");
		}
		return ajaxResult;
	}
	/**
	 * 
	 * @MethodName：checkCode
	 * @param code
	 * @return
	 * @ReturnType：boolean
	 * @Description：
	 * @Creator：xiedong
	 * @CreateTime：2017年12月20日下午4:28:19
	 * @Modifier：
	 * @ModifyTime：
	 */
	@RequestMapping("/checkCode")
	@ResponseBody
	public boolean checkCode(String code) {
		boolean isValidate = true;
		try {
			Integer count = systemDictionaryService.getCodeCount(code);
			if (count != null && count >= 1) {
				isValidate = false;
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isValidate;
	}
}
