package com.ccl.web.base;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.ui.Model;


/**
 * 
 * @ClassName：BaseController
 * @Description：
 * @author：xiedong
 * @Date：2017年12月14日下午5:51:37
 * @version：1.0.0
 */
public class BaseController extends AbstractBaseController {
	
	protected final Logger log = org.slf4j.LoggerFactory.getLogger(BaseController.class);
	
	@Autowired
	private Environment env;
	
	@Override
	protected String listUI(Model model) throws Exception{
		return null;
	}
	
	protected Integer getConfigExportCount(){
		return Integer.valueOf(env.getProperty("exportCount"));
	}
}