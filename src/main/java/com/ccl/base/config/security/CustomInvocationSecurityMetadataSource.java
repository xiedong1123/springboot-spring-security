package com.ccl.base.config.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Service;

import com.ccl.core.dao.IResourceDao;
import com.ccl.core.entity.Resource;
import com.ccl.core.entity.Role;

/**
 * 这个类用于加载用户和用户的权限到spring security的的缓存中...这里选择从数据库加载
 * 
 * @author chenchuanliang
 *
 */
@Service
public class CustomInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

	private static Map<String, Collection<ConfigAttribute>> resourceMap = null;
	private org.slf4j.Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private IResourceDao resourceDao;

	/**
	 * 加载资源，初始化资源变量
	 * @throws Exception 
	 */
	@PostConstruct
	public void loadResourceDefine() {
//		if (resourceMap == null) {
			resourceMap = new HashMap<>();
			List<Resource> loadResourceDefine = resourceDao.loadResourceDefine();
			for (Resource resource : loadResourceDefine) {
				Collection<ConfigAttribute> atts = new ArrayList<ConfigAttribute>(); 
				ConfigAttribute ca = new SecurityConfig("*"); 
				atts.add(ca);
				List<Role> roles = resource.getRoles();
				for (Role role : roles) {
					atts.add(role);
				}
				resourceMap.put(resource.getUrl().trim(), atts);
			}
//		}
		log.info("权限资源关系加载成功!!");
	}

	@Override
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
		loadResourceDefine();
		//object 中包含用户请求的request 信息
        HttpServletRequest request = ((FilterInvocation) object).getHttpRequest();
        String servletPath = request.getServletPath();// 获取请求路径
       // AntPathRequestMatcher matcher;
        String resUrl;
        for(Iterator<String> iter = resourceMap.keySet().iterator(); iter.hasNext(); ) {
            resUrl = iter.next();
           // matcher = new AntPathRequestMatcher(resUrl);
            if(servletPath.indexOf(resUrl) != -1) {
                return resourceMap.get(resUrl);
            }
        }
        return null;
	}

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return null;
	}

	@Override
	public boolean supports(Class<?> aClass) {
		return true;
	}
	
}
