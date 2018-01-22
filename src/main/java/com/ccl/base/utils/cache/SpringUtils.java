/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.ccl.base.utils.cache;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
/**
 * 
 *	类名称：SpringUtils
 *	类描述：spring上下文工具类型
 *	创建人：
 *	创建时间：2016年11月10日上午9:52:55
 *	修改人：
 *	修改时间：
 *	修改备注：
 */
public final class SpringUtils implements BeanFactoryPostProcessor {

	/*
	 * 注入上下文对象
	 */
    private static ConfigurableListableBeanFactory beanFactory; // Spring应用上下文环境
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        SpringUtils.beanFactory = beanFactory;
    }

    /**
     * 
     *	方法名：getBean
     *	@param name
     *	@return
     *	@throws BeansException
     *	返回类型：T
     *	说明：根据bean的name获取实例化对象
     *	创建人：
     * 	创建日期：2016年11月10日上午9:55:13
     *	修改人：
     *	修改日期：
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) throws BeansException {
        return (T) beanFactory.getBean(name);
    }

    /**
     * 
     *	方法名：getBean
     *	@param clz
     *	@return
     *	@throws BeansException
     *	返回类型：T
     *	说明：根据bean的class类类型（如：User.class）获取实例化对象
     *	创建人：
     * 	创建日期：2016年11月10日上午9:55:51
     *	修改人：
     *	修改日期：
     */
    public static <T> T getBean(Class<T> clz) throws BeansException {
        T result = (T) beanFactory.getBean(clz);
        return result;
    }

    /**
     * 
     *	方法名：containsBean
     *	@param name
     *	@return
     *	返回类型：boolean
     *	说明：根据bean的name判断上下文中是否包含这个bean
     *	创建人：
     * 	创建日期：2016年11月10日上午9:57:08
     *	修改人：
     *	修改日期：
     */
    public static boolean containsBean(String name) {
        return beanFactory.containsBean(name);
    }

    /** 
     *	方法名：isSingleton
     *	@param name
     *	@return
     *	@throws NoSuchBeanDefinitionException
     *	返回类型：boolean
     *	说明： 判断以给定名字注册的bean定义是一个singleton还是一个prototype。 
     *        如果与给定名字相应的bean定义没有被找到，将会抛出一个异常（NoSuchBeanDefinitionException）
     *	创建人：
     * 	创建日期：2016年11月10日上午9:58:25
     *	修改人：
     *	修改日期：
     */
    public static boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
        return beanFactory.isSingleton(name);
    }

    /**
     * 
     *	方法名：getType
     *	@param name
     *	@return
     *	@throws NoSuchBeanDefinitionException
     *	返回类型：Class<?>
     *	说明：根据bean的name获取注册对象的class类类型
     *	创建人：
     * 	创建日期：2016年11月10日上午9:59:55
     *	修改人：
     *	修改日期：
     */
    public static Class<?> getType(String name) throws NoSuchBeanDefinitionException {
        return beanFactory.getType(name);
    }

    /**
     * 
     *	方法名：getAliases
     *	@param name
     *	@return
     *	@throws NoSuchBeanDefinitionException
     *	返回类型：String[]
     *	说明：根据bean的name获取bean的所有别名
     *		    如果给定的bean名字在bean定义中有别名，则返回这些别名
     *	创建人：
     * 	创建日期：2016年11月10日上午10:00:49
     *	修改人：
     *	修改日期：
     */
    public static String[] getAliases(String name) throws NoSuchBeanDefinitionException {
        return beanFactory.getAliases(name);
    }

}
