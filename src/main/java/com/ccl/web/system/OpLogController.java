	 
package com.ccl.web.system;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ccl.base.utils.Const;
import com.ccl.base.utils.LogUtils;
import com.ccl.base.utils.page.PageResult;
import com.ccl.core.entity.OpLog;
import com.ccl.core.query.OpLogQuery;
import com.ccl.core.service.IOpLogService;
import com.ccl.web.base.BaseController;

/**
 * 
 * @ClassName：OpLogController
 * @Description：
 * @author：xiedong
 * @Date：2017年12月14日下午5:47:36
 * @version：1.0.0
 */
@Controller
@RequestMapping("/log")
public class OpLogController extends BaseController{
	
	private static final Logger logger = LoggerFactory.getLogger(OpLogController.class);
	
	@Autowired
	IOpLogService opLogServiceImpl;
	
	/**
	 * @Description 页面跳转
	 * @param id
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Override
	@RequestMapping("/listUI")
	public String listUI(Model model) throws Exception {
		return Const.SYSTEM + "/log/list";
	}

	/**
	 * 
	 * @MethodName：parameterLogList
	 * @param opLogQuery
	 * @return
	 * @throws Exception
	 * @ReturnType：PageResult<OpLog>
	 * @Description：
	 * @Creator：xiedong
	 * @CreateTime：2017年12月14日下午5:47:46
	 * @Modifier：
	 * @ModifyTime：
	 */
	@RequestMapping("/pagelist")
	@ResponseBody
	public PageResult<OpLog> parameterLogList(OpLogQuery opLogQuery) throws Exception {
			LogUtils.info(logger,"操作日志查询接口进入，入参{}",opLogQuery);
		PageResult<OpLog> sysLogList = null;
		try {
			if(StringUtils.isNotBlank(opLogQuery.getOperatTime())) {
				String[] split = opLogQuery.getOperatTime().split("~");
				opLogQuery.setStartTime(split[0]);
				opLogQuery.setEndTime(split[1]);
			}
			sysLogList = opLogServiceImpl.findPage(opLogQuery);
			LogUtils.info(logger,"操作日志查询成功");
		} catch (Exception e) {
			e.printStackTrace();
			LogUtils.error(logger,"操作日志查询失败");
		}
		return sysLogList;
	}
}
