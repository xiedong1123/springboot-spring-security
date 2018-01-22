package com.ccl.web.system;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ccl.base.config.security.SecurityUtil;
import com.ccl.base.utils.Const;
import com.ccl.core.entity.Resource;
import com.ccl.core.entity.User;
import com.ccl.core.service.IResourceService;

/**
 * 
 * @ClassName：MainController
 * @Description：
 * @author：xiedong
 * @Date：2017年12月14日下午5:47:52
 * @version：1.0.0
 */
@Controller
public class MainController {

    @Autowired
    private IResourceService resourceService;
    
    /**
     * 
     *	方法名：index
     *	@param model
     *	@return
     *	返回类型：String
     *	说明：
     *	创建人：
     * 	创建日期：2016年11月10日下午2:38:28
     *	修改人：
     *	修改日期：
     * @throws Exception 
     */
    @RequestMapping("/main")
    public String index(Model model) throws Exception {
    	User user = SecurityUtil.getLoginUser();
    	List<Resource> menus = resourceService.getMenus(user);
        model.addAttribute("menus", menus);
        return Const.SYSTEM + "/main";
    } 
    
    /**
     * 
     *	方法名：welcome
     *	@return
     *	返回类型：String
     *	说明：欢迎页面（首页中间主要部分内容）
     *	创建人：
     * 	创建日期：2016年11月8日下午6:07:48
     *	修改人：
     *	修改日期：
     */
    @RequestMapping("/welcome")
    public String welcome() {
        return Const.SYSTEM + "/welcome";
    }
    
    
    
}
