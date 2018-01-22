package com.ccl.web.system;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ccl.base.utils.AjaxResult;
import com.ccl.base.utils.Const;
import com.ccl.base.utils.page.PageResult;
import com.ccl.core.entity.Resource;
import com.ccl.core.query.ResourceQuery;
import com.ccl.core.service.IResourceService;
import com.ccl.web.base.BaseController;


/**
 * 
 * @ClassName：ResourceController
 * @Description：
 * @author：xiedong
 * @Date：2017年12月14日下午5:43:20
 * @version：1.0.0
 */
@Controller
@RequestMapping("/resource")
public class ResourceController extends BaseController{

    @Autowired
    private IResourceService resourceService;
    /**
     * 
     *	方法名：listUI
     *	@param model
     *	@return
     *	返回类型：String
     *	说明：列表UI
     *	创建人：
     * 	创建日期：2016年11月10日下午3:22:51
     *	修改人：
     *	修改日期：
     * @throws Exception 
     */
    @Override
	@RequestMapping("/listUI")
    public String listUI(Model model) throws Exception {
        return Const.SYSTEM + "/resource/list";
    }
	/**
	 * 
	 * @MethodName：getTreeList
	 * @return
	 * @throws Exception
	 * @ReturnType：List<Resource>
	 * @Description：
	 * @Creator：xiedong
	 * @CreateTime：2017年12月14日上午10:16:33
	 * @Modifier：
	 * @ModifyTime：
	 */
	@RequestMapping("/getTreeList")
	@ResponseBody
	public List<Resource> getTreeList() throws Exception{
		List<Resource> returnList = null;
		try {
			returnList = resourceService.getTreelist();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnList;
	}
    
    /**
     * 
     *	方法名：getPermissionsByRoleId
     *	@param id
     *	@return
     *	@throws Exception
     *	返回类型：List<Resource>
     *	说明：根据角色Id获取角色权限
     *	创建人：
     * 	创建日期：2016年12月9日下午6:05:21
     *	修改人：
     *	修改日期：
     */
    @ResponseBody
    @RequestMapping("getPermissionsByRoleId")
    public AjaxResult getPermissionsByRoleId(String roleId) throws Exception {
    	AjaxResult ajaxResult =  null ;
    	try {
    		List<Resource> mypermision  = resourceService.getPermissionsByRoleIds(roleId);
    		ajaxResult = new AjaxResult(true, mypermision);;
		} catch (Exception e) {
			e.printStackTrace();
			ajaxResult = new AjaxResult(false,"查询失败!");;
		}
    	return ajaxResult;
    }
    /**
     * 
     *	方法名：pageList
     *	@param resourceQuery
     *	@param model
     *	@return
     *	@throws Exception
     *	返回类型：PageResult<Resource>
     *	说明：
     *	创建人：
     * 	创建日期：2017年3月6日上午11:22:21
     *	修改人：
     *	修改日期：
     */
    @RequestMapping("/pagelist")
    @ResponseBody
    public PageResult<Resource> pageList(ResourceQuery resourceQuery,Model model) throws Exception {
    	PageResult<Resource> resourceList = null;
    	try {
    		resourceList = resourceService.findPage(resourceQuery);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return resourceList;
    }


}
