package com.ccl.base.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * @ 通用获取properties文件中的属性值
 * @author xchm
 *
 */
public class PropertiesGet {
	private static Map<String, ResourceBundle> promap = new HashMap<String, ResourceBundle>();

	/**
	 * 获取properties属性
	 * @param key
	 * @param propertiesfile 比如 common ,jdbc,cache
	 * @return
	 */

	public static String getProperty(String key, String propertiesfile) {
		ResourceBundle rb = promap.get(propertiesfile);
		if (rb == null) {
			rb = ResourceBundle.getBundle(propertiesfile);
			promap.put(propertiesfile, rb);
		}
		String value = rb.getString(key);
		return value;
	}
	
	
	public static String getRoleProperty(String key) {
		ResourceBundle rb = promap.get("config/role");
		if (rb == null) {
			rb = ResourceBundle.getBundle("config/role");
			promap.put("config/role", rb);
		}
		String value = rb.getString(key);
		return value;
	}
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		System.out.println(PropertiesGet.getProperty("uniformUrl", "common"));
//		System.out.println(PropertiesGet.getProperty("devUserTotalIp", "outsystem"));
		System.out.println(getRoleProperty("type"));
	}

}
