package com.ccl.base.utils;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ccl.base.config.security.SecurityUtil;
import com.ccl.core.entity.OpLog;
import com.ccl.core.entity.User;
import com.ccl.core.service.IOpLogService;

public class LogUtils {


	/**
	 * @MethodName：get
	 * @param clazz 日志发出的类
	 * @return
	 * @ReturnType：Logger
	 * @Description： 
	 * @Creator：liuyu
	 * @CreateTime：2017年5月20日下午1:37:13
	 * @Modifier：
	 * @ModifyTime：
	 */
		
	public static Logger get(Class<?> clazz) {
		return LoggerFactory.getLogger(clazz);
	}


	
	/**
	 * @MethodName：get
	 * @param name  自定义的日志发出者名称
	 * @return
	 * @ReturnType：Logger
	 * @Description：
	 * @Creator：liuyu
	 * @CreateTime：2017年5月20日下午1:37:26
	 * @Modifier：
	 * @ModifyTime：
	 */
		
	public static Logger get(String name) {
		return LoggerFactory.getLogger(name);
	}


	
	/**
	 * 
	 * @MethodName：get
	 * @return
	 * @ReturnType：Logger
	 * @Description： 获得日志，自动判定日志发出者
	 * @Creator：liuyu
	 * @CreateTime：2017年5月20日下午1:37:41
	 * @Modifier：
	 * @ModifyTime：
	 */
		
	public static Logger get() {
		StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
		return LoggerFactory.getLogger(stackTrace[2].getClassName());
	}


	/**
	 * 
	 * @MethodName：trace
	 * @param log   日志对象
	 * @param format  格式文本，{} 代表变量
	 * @param arguments  变量对应的参数
	 * @ReturnType：void
	 * @Description： Trace等级日志，小于Debug
	 * @Creator：liuyu
	 * @CreateTime：2017年5月20日下午1:36:28
	 * @Modifier：
	 * @ModifyTime：
	 */
		
	public static void trace(Logger log, String format, Object... arguments) {
		log.trace(format, arguments);
	}


	/**
	 * 
	 * @MethodName：debug
	 * @param log 日志对象
	 * @param format  格式文本，{} 代表变量
	 * @param arguments  变量对应的参数
	 * @ReturnType：void
	 * @Description： Debug等级日志，小于Info
	 * @Creator：liuyu
	 * @CreateTime：2017年5月20日下午1:38:17
	 * @Modifier：
	 * @ModifyTime：
	 */
		
	public static void debug(Logger log, String format, Object... arguments) {
		log.debug(format, arguments);
	}

	
	/**
	 * @MethodName：info
	 * @param log         日志对象
	 * @param format       格式文本，{} 代表变量
	 * @param arguments    变量对应的参数
	 * @ReturnType：void
	 * @Description：  info信息记录  
	 * @Creator：liuyu
	 * @CreateTime：2017年5月20日
	 * @Modifier：
	 * @ModifyTime：
	 */
		
	public static void info(Logger log, String format, Object... arguments) {

		if(log.isInfoEnabled()){
			
			log.info(format, arguments);		
		}
	}


	/**
	 * @MethodName：warn
	 * @param log  日志对象
	 * @param format  格式文本，{} 代表变量
	 * @param arguments   变量对应的参数
	 * @ReturnType：void
	 * @Description：Warn等级日志，小于Error
	 * @Creator：liuyu
	 * @CreateTime：2017年5月20日下午1:40:27
	 * @Modifier：
	 * @ModifyTime：
	 */
		
	public static void warn(Logger log, String format, Object... arguments) {
		
		if(log.isWarnEnabled()){
			
			log.warn(format, arguments);
		}
	}
	
	
	/**
	 * @MethodName：warn
	 * @param log  日志对象
	 * @param format  格式文本，{} 代表变量
	 * @param arguments   变量对应的参数
	 * @ReturnType：void
	 * @Description：rpc调用日志 warn信息记录    每一个会话，唯一标示UUID串联所有日志
	 * @Creator：liuyu
	 * @CreateTime：2017年5月20日下午1:40:27
	 * @Modifier：
	 * @ModifyTime：
	 */
	public static void rpcWarn(Logger log, String format, Object... arguments) {
		if(log.isWarnEnabled()){
			
			log.warn(format, arguments);
		}
		
	}


	
	/**
	 * @MethodName：warn
	 * @param log　　　日志对象
	 * @param e　　　　　需在日志中堆栈打印的异常
	 * @param format　　格式文本，{} 代表变量
	 * @param arguments　　变量对应的参数
	 * @ReturnType：void
	 * @Description：rpc调用日志 warn信息记录    每一个会话，唯一标示UUID串联所有日志
	 * @Creator：liuyu
	 * @CreateTime：2017年5月20日下午1:43:52
	 * @Modifier：
	 * @ModifyTime：
	 */
		
	public static void warn(Logger log, Throwable e, String format, Object... arguments) {
		if(log.isWarnEnabled()){
			
			log.warn(format(format, arguments), e);
		}
	}
	
	
	
	/**
	 * @MethodName：error
	 * @param log   日志对象
	 * @param format   格式文本，{} 代表变量
	 * @param arguments  变量对应的参数
	 * @ReturnType：void
	 * @Description：
	 * @Creator：liuyu
	 * @CreateTime：2017年5月20日下午1:35:12
	 * @Modifier：
	 * @ModifyTime：
	 */
		
	public static void error(Logger log, String format, Object... arguments) {
		
		log.error(format, arguments);
	}
	

	/**
	 * @MethodName：error
	 * @param log  日志对象
	 * @param e    需在日志中堆栈打印的异常
	 * @param format     格式文本，{} 代表变量
	 * @param arguments  变量对应的参数
	 * @ReturnType：void
	 * @Description：
	 * @Creator：liuyu
	 * @CreateTime：2017年5月20日下午1:33:33
	 * @Modifier：
	 * @ModifyTime：
	 */
		
	public static void error(Logger log, Throwable e, String format, Object... arguments) {
		
		log.error(format(format, arguments), e);
	}
	

	// ----------------------------------------------------------- Private
	// method start

	/**
	 * @MethodName：format
	 * @param template  文本模板，被替换的部分用 {} 表示
	 * @param values  参数值
	 * @return         格式化后的文本
	 * @ReturnType：String
	 * @Description：
	 * @Creator：liuyu
	 * @CreateTime：2017年5月20日下午1:32:50
	 * @Modifier：
	 * @ModifyTime：
	 */
		
	private static String format(String template, Object... values) {
		return String.format(template.replace("{}", "%s"), values);
	}



	
	
	/**
	 * @MethodName：get32UUID
	 * @return
	 * @ReturnType：String
	 * @Description： 获取32位UUID
	 * @Creator：liuyu
	 * @CreateTime：2017年5月20日下午1:32:10
	 * @Modifier：
	 * @ModifyTime：
	 */
		
	public static  String get32UUID() {
		UUID uuid = UUID.randomUUID();
		String returnValue = uuid.toString();
		returnValue = returnValue.replace("-", "");
		return returnValue;
	}
	
	
	// method end
	
	//------------------------------------------------------------- opreateLog
	private static   IOpLogService opLogService = null;
	public static void writeOperateLog( String operatAddress ,String contentFormate , Object... arguments) throws Exception{
		try {
			User user = SecurityUtil.getLoginUser();
			
			getOpLogService().save(new OpLog(user.getId(),operatAddress,format(contentFormate,arguments)));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
	}
	



	private static IOpLogService getOpLogService(){
		if(null == opLogService){
			
			opLogService = (IOpLogService)SpringContextHolder.getBean("opLogServiceImpl");
		}
		return  opLogService ;
		 
	}

	
}
