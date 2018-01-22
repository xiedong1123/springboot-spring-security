package com.ccl.web.system;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ccl.base.config.security.SecurityUtil;
import com.ccl.base.utils.AjaxResult;
import com.ccl.base.utils.LogUtils;
import com.ccl.base.utils.page.PageResult;
import com.ccl.core.entity.SystemDictionaryItem;
import com.ccl.core.entity.User;
import com.ccl.core.query.SystemDictionaryItemQuery;
import com.ccl.core.service.ISystemDictionaryItemService;
import com.ccl.web.base.BaseController;

/**
 * 
 * @ClassName：SystemDictionaryItemController
 * @Description：
 * @Author：xiedong
 * @Date：2017年12月20日下午4:32:07
 * @version：1.0.0
 */
@Controller
@RequestMapping("/systemDictionaryItem")
public class SystemDictionaryItemController extends BaseController {
	
	private static final Logger logger = LoggerFactory.getLogger(SystemDictionaryItemController.class);
	
	@Autowired
	private ISystemDictionaryItemService systemDictionaryItemService;
	
	
	/**
	 * 
	 * @MethodName：pageList
	 * @param code
	 * @param systemDictionaryItemQuery
	 * @return
	 * @ReturnType：PageResult<SystemDictionaryItem>
	 * @Description：
	 * @Creator：xiedong
	 * @CreateTime：2017年12月20日下午4:32:10
	 * @Modifier：
	 * @ModifyTime：
	 */
	@RequestMapping("/pageList/{code}")
	@ResponseBody
	public PageResult<SystemDictionaryItem> pageList(@PathVariable("code") String code,SystemDictionaryItemQuery systemDictionaryItemQuery){
		LogUtils.info(logger,"分页数据查询入口code{}:systemDictionaryItemQuery{}",code,systemDictionaryItemQuery);
		PageResult<SystemDictionaryItem> findPage = null;
		try {
			systemDictionaryItemQuery.setCode(code);
			findPage = systemDictionaryItemService.findPage(systemDictionaryItemQuery);
			LogUtils.info(logger,"分页数据查询成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return findPage;
	}
	
	
	/**
	 * 
	 * @MethodName：save
	 * @param systemDictionaryItem
	 * @return
	 * @ReturnType：AjaxResult
	 * @Description：
	 * @Creator：xiedong
	 * @CreateTime：2017年12月20日下午4:32:21
	 * @Modifier：
	 * @ModifyTime：
	 */
	@RequestMapping("/save")
	@ResponseBody
	public AjaxResult save(SystemDictionaryItem systemDictionaryItem) {
		LogUtils.info(logger,"保存入口systemDictionaryItem{}",systemDictionaryItem);
		AjaxResult ajaxResult = null;
		try {
			systemDictionaryItem.setCreateTime(new Date());
			systemDictionaryItemService.save(systemDictionaryItem);
			ajaxResult = new AjaxResult(true, "保存成功");
			LogUtils.info(logger,"保存成功");
			User user = SecurityUtil.getLoginUser();
			LogUtils.writeOperateLog("数据字典", "{}账号:{},新增code为:[{}]的明细,唯一标识[{}]", user.getName(),user.getUsername(),systemDictionaryItem.getCode(),systemDictionaryItem.getValue());
		} catch (Exception e) {
			e.printStackTrace();
			LogUtils.info(logger,"保存失败");
			ajaxResult = new AjaxResult(false, "保存失败");
		}
		return ajaxResult;
	}
	
	
	/**
	 * 
	 * @MethodName：edit
	 * @param systemDictionaryItem
	 * @return
	 * @ReturnType：AjaxResult
	 * @Description：
	 * @Creator：xiedong
	 * @CreateTime：2017年12月20日下午4:32:25
	 * @Modifier：
	 * @ModifyTime：
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public AjaxResult edit(SystemDictionaryItem systemDictionaryItem) {
		LogUtils.info(logger,"编辑入口systemDictionaryItem{}",systemDictionaryItem);
		AjaxResult ajaxResult = null;
		try {
			systemDictionaryItem.setUpdateTime(new Date());
			systemDictionaryItemService.update(systemDictionaryItem);
			ajaxResult = new AjaxResult(true, "修改成功");
			User user = SecurityUtil.getLoginUser();
			LogUtils.writeOperateLog("数据字典", "{}账号:{},编辑code为:[{}]的明细,唯一标识[{}]", user.getName(),user.getUsername(),systemDictionaryItem.getCode(),systemDictionaryItem.getValue());
		} catch (Exception e) {
			e.printStackTrace();
			ajaxResult = new AjaxResult(false, "修改失败");
		}
		return ajaxResult;
	}
	
	
	/**
	 * 
	 * @MethodName：delete
	 * @param id
	 * @return
	 * @ReturnType：AjaxResult
	 * @Description：
	 * @Creator：xiedong
	 * @CreateTime：2017年12月20日下午4:32:28
	 * @Modifier：
	 * @ModifyTime：
	 */
	@RequestMapping("/delete/{id}")
	@ResponseBody
	public AjaxResult delete(@PathVariable("id")Long id) {
		AjaxResult ajaxResult = null;
		try {
			SystemDictionaryItem systemDictionaryItem = systemDictionaryItemService.getById(id);
			systemDictionaryItemService.deleteById(id);
			ajaxResult = new AjaxResult(true, "删除成功");
			User user = SecurityUtil.getLoginUser();
			LogUtils.writeOperateLog("数据字典", "{}账号:{},删除code为[{}],ID为[{}],value为[{}],字典明细", user.getName(),user.getUsername(),systemDictionaryItem.getCode(),id,systemDictionaryItem.getValue());
		} catch (Exception e) {
			e.printStackTrace();
			ajaxResult = new AjaxResult(false, "删除失败");
		}
		return ajaxResult;
	}
	
	
	/**
	 * 
	 * @MethodName：isValueExist
	 * @param value
	 * @param id
	 * @return
	 * @ReturnType：Boolean
	 * @Description：
	 * @Creator：xiedong
	 * @CreateTime：2017年12月20日下午4:32:32
	 * @Modifier：
	 * @ModifyTime：
	 */
	@ResponseBody
	@RequestMapping("/isValueExist")
	public Boolean isValueExist (String value,Long id){
		Boolean result = true;
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("value", value.trim());
		List<SystemDictionaryItem> values = null;
		try {
			values = systemDictionaryItemService.getByWhere(params);
			if (id==null) {
				result = values.size()>0?false:true;
			}else {
				SystemDictionaryItem systemDictionaryItem = systemDictionaryItemService.getById(id);
				if (!systemDictionaryItem.getValue().equals(value)) {
					result = values.size()>0?false:true;
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
