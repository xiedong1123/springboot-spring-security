package com.ccl.base.utils;

import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

@Service
public class SpringContextHolder implements ApplicationListener<ContextRefreshedEvent> {

	private static ApplicationContext applicationContext = null;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (applicationContext == null) {
			applicationContext = event.getApplicationContext();
		}
	}

	/*
	 * ApplicationContext context=
	 * ContextLoader.getCurrentWebApplicationContext();//尝试下这个方法
	 */
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	// 从静态变量ApplicationContext中取得Bean, 自动转型为所赋值对象的类型.
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name) {
		checkApplicationContext();
		return (T) applicationContext.getBean(name);
	}

	// 从静态变量ApplicationContext中取得Bean, 自动转型为所赋值对象的类型.
	// 如果有多个Bean符合Class, 取出第一个.
	@SuppressWarnings("unchecked")
	public static <T> T getBean(Class<T> clazz) {
		checkApplicationContext();
		@SuppressWarnings("rawtypes")
		Map beanMaps = applicationContext.getBeansOfType(clazz);
		if (beanMaps != null && !beanMaps.isEmpty()) {
			return (T) beanMaps.values().iterator().next();
		} else {
			return null;
		}
	}

	private static void checkApplicationContext() {
		if (applicationContext == null) {
			throw new IllegalStateException("applicaitonContext未注入,请在applicationContext.xml中定义SpringContextHolder");
		}
	}

}