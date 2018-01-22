package com.ccl.web.base;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.ccl.base.config.security.SecurityUtil;
import com.ccl.core.entity.Resource;
import com.ccl.core.entity.User;
import com.ccl.core.service.IResourceService;

/**
 * 
 * @ClassName：AbstractBaseController
 * @Description：
 * @author：xiedong
 * @Date：2017年12月14日下午5:51:02
 * @version：1.0.0
 */
public abstract class AbstractBaseController {
	
	private static final Logger log = LoggerFactory.getLogger(AbstractBaseController.class);
	
	@Autowired
	private IResourceService resourceService;
	
	/**
	 * 
	 * @MethodName：getButtonsList
	 * @param id
	 * @return
	 * @throws Exception
	 * @ReturnType：List<List<String>>
	 * @Description：获取用户功能按钮
	 * @Creator：xiedong
	 * @CreateTime：2017年5月26日上午9:05:43
	 * @Modifier：
	 * @ModifyTime：
	 */
	public List<List<String>> getButtonsList(Long id) throws Exception{
		List<List<String>> btnList = new ArrayList<>();
		List<String> btnHtmlList = new ArrayList<>();
		List<String> resKeyList2 = new ArrayList<>();
		JSONObject jsonObject = new JSONObject();
		if(id != null && !"".equals(id)){
			User user = SecurityUtil.getLoginUser();
			List<Resource> resList = resourceService.getByUser(id,user);
			for (Resource res : resList) {
				if(StringUtils.isNotEmpty(res.getKey())){
					if(res.getBtnType()== 1){
						btnHtmlList.add(res.getBtnHtml());
					}else{
						//resKeyList.add(res.getKey());
						//resKeyList1.add(res.getBtnHtml());
						jsonObject.put(res.getKey(), res.getBtnHtml());
					}
					//btnList.add(resKeyList);
					//log.info("------------------------resKeyList----------:"+resKeyList);
					//btnList.add(resKeyList1);
					//log.info("------------------------resKeyList1----------:"+resKeyList1);
				}
			}
			btnList.add(btnHtmlList);
			log.info("------------------------btnHtmlList----------:"+btnHtmlList);
			resKeyList2.add(jsonObject.toString());
			log.info("------------------------resKeyList2----------:"+resKeyList2);
			btnList.add(resKeyList2);
		}
		return btnList;
	}
	/**
	 * 
	 * @MethodName：listUI
	 * @param id
	 * @param model
	 * @return
	 * @throws Exception
	 * @ReturnType：String
	 * @Description：列表UI
	 * @Creator：xiedong
	 * @CreateTime：2017年5月26日上午12:15:29
	 * @Modifier：
	 * @ModifyTime：
	 */
	@RequestMapping("/listUI{id}")
	public final String listUI(@PathVariable("id") Long id,Model model) throws Exception{
		List<List<String>> buttonsList = this.getButtonsList(id);
		if(buttonsList.size()>0){
			model.addAttribute("btnHtmls", buttonsList.get(0));
			model.addAttribute("resKeys", buttonsList.get(1).get(0));
			//List<String> list = this.getButtonsList(id).get(2);
			//List<String> list2 = this.getButtonsList(id).get(3);
			//String resKeys = "";
			//for (String str : list) {
				//if(StringUtils.isNotBlank(str)) {
				//	resKeys += str;
				//}
			//}
			//model.addAttribute("resKeys1", resKeys);
			//model.addAttribute("resKeys2", list2.get(0));
			log.info("-------------resKeys---------------:"+buttonsList.get(1).get(0));
			//log.info("-------------resKeys1---------------:"+resKeys);
			//log.info("-------------resKeys2---------------:"+list2.get(0));
			log.info("-------------btnHtmls---------------:"+buttonsList.get(0));
		}else{
			log.error("获取功能按钮失败!");
		}
    	  return this.listUI(model);
	}
	/**
	 * 
	 * @MethodName：listUI
	 * @param model
	 * @return
	 * @throws Exception
	 * @ReturnType：String
	 * @Description：页面跳转
	 * @Creator：xiedong
	 * @CreateTime：2017年12月14日下午5:56:27
	 * @Modifier：
	 * @ModifyTime：
	 */
	protected  abstract String listUI(Model model) throws Exception;
}