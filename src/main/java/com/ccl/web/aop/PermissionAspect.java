package com.ccl.web.aop;

import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ccl.base.utils.crypt.RSAUtils;
import com.ccl.base.utils.page.PageResult;

import net.sf.json.JSONObject;

@Aspect
@Component()
public class PermissionAspect {
	private static final Logger logger = LoggerFactory.getLogger(PermissionAspect.class);
	
	
	@Autowired
	HttpServletRequest request;
	@Autowired
	HttpServletResponse response;
	
	@Pointcut("execution (* com.ccl.web.system..*.*(..))")
	public void controllerAspect() {
	}// 定义切点 （web层）

	// 定义一个环绕通知
	@SuppressWarnings("rawtypes")
	@Around("controllerAspect()")
	public Object aroundMethod(ProceedingJoinPoint joinPoint) throws Throwable {
		Object result = joinPoint.proceed();
	/*	Map<String, String[]> parameterMap = request.getParameterMap();
		Iterator<Entry<String, String[]>> iterator = parameterMap.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry entry = (Map.Entry) iterator.next();
			Object key = entry.getKey();
			Object val = entry.getValue();
		}*/
		
		
		String targetName = joinPoint.getTarget().getClass().getName();
		String methodName = joinPoint.getSignature().getName();
		Object[] arguments = joinPoint.getArgs();
		Class targetClass = Class.forName(targetName);
		Method[] methods = targetClass.getMethods();
		for (Method method : methods) {
			if (method.getName().equals(methodName)) {
				Class[] clazzs = method.getParameterTypes();
				if (clazzs.length == arguments.length) {
					Sign sign = method.getAnnotation(Sign.class);
					if (sign != null) {
						JSONObject jsonObject = new JSONObject(); 
						Enumeration<String> parameterNames = request.getParameterNames();
						while (parameterNames.hasMoreElements()) {  
							  String paramName = (String) parameterNames.nextElement();  
							   
							  String paramValue = request.getParameter(paramName);  
							  
							  jsonObject.put(paramName,paramValue);  
							  }
						String str = (String)jsonObject.get("sign");
						if(StringUtils.isNotBlank(str)){
							jsonObject.remove("sign");
							jsonObject.remove("page");
							jsonObject.remove("limit");
							String gy = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDjteZezR2wTSS703epft8bLyp8eT/gtJ3L8toYOP/2bDC0WcoTg/oVW3KiysevqGuVHqxQezf0hWJLhvrhhEEQeGqYEQhiK8IPoarPJSbUeqZPDFrg7e/hxP0ygu3D75/2npySwrnD6N0xj7Wp3uab041vk9MuHXNGYxd7KNGRhQIDAQAB";
							boolean verify = RSAUtils.getInstance().verify(getSortJson(jsonObject).toString().getBytes(), gy, str);
							logger.info("验签状态:"+verify);
							if(!verify){
								logger.info("--------------验签失败------------------");
								PageResult<Object> pageResult = new PageResult<>();
								pageResult.setCode(401);
								pageResult.setMsg("验签失败");
								result = pageResult;
							}
						}else{
							logger.info("--------------验签失败------------------");
							PageResult<Object> pageResult = new PageResult<>();
							pageResult.setCode(401);
							pageResult.setMsg("非法请求");
							result = pageResult;
						}
					}
				}
			}
		}
		
		return result;
		
	}
	
	/** 
     * 对单层json进行key字母排序 
     * @param json 
     * @return 
     */  
    @SuppressWarnings("unchecked")
	public static JSONObject getSortJson(JSONObject json){  
        Iterator<String> iteratorKeys = json.keys();  
        SortedMap<String, String> map = new TreeMap<String, String>();    
        while (iteratorKeys.hasNext()) {    
                String key = iteratorKeys.next().toString();    
                String vlaue = json.optString(key);    
                map.put(key, vlaue);    
        }    
        JSONObject json2 = JSONObject.fromObject(map);  
        return json2;  
    }
}
