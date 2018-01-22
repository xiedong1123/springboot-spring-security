package com.ccl.base.utils.jpush;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
/**
 * 
 *	类名称：JPushUtils
 *	类描述：极光推送工具类
 *	创建人：
 *	创建时间：2016年9月21日上午9:14:19
 *	修改人：
 *	修改时间：
 *	修改备注：
 */
public class JPushUtils {
	private static Logger logger = LoggerFactory.getLogger(JPushUtils.class);
//	private static String masterSecret1="78e4f189f177b4ca7ce5a0cb";//测试
//	private static String cusKey="bb6bc14e552774e3676d8c9c";
	/**
	 * 公众端masterSecret1
	 */
	public static String GZ_MASTERSECRET="eab114c3cc2316bc0c389105";
	/**
	 * 公众端cusKey
	 */
	public static String GZ_CUSKEY="20c24fa8807a2df9fcaeeb8b";
	/**
	 * 法律服务端masterSecret1
	 */
	public static String FW_MASTERSECRET="4350b9097ba458189fabcd02";
	/**
	 * 法律服务端cusKey
	 */
	public static String FW_CUSKE="e171ecea6a231f9d052e5446";
	/**
	 * 工作人员端masterSecret1
	 */
	public static String NB_MASTERSECRET="c3a929a84b565a2f1c19f1e1";
	/**
	 * 工作人员端cusKey
	 */
	public static String NB_CUSKEY="ae44b9613b918af6c9333945";
	
	/**法律服务消息推送内容*/
	public static final String CONTENT_WORK = "您有新的法律援助申请需要审核/处理，请查看";
	public static final String CONTENT = "您的法律援助申请有状态更新，请查看";
	/**人民调解推送内容*/
	public static final String RM_CONTENT_WORK = "您有新的人民调解申请需要处理，请查看";
	public static final String RM_CONTENT = "您的人民调解申请有状态更新，请查看";
	/**消息类型     分类咨询*/
	public static final String FL = "1";
	/**消息类型   专家咨询*/
	public static final String ZJ = "2";
	/**消息类型     举报*/
	public static final String JB = "3";
	/**消息类型    公众端援助申请 状态更新*/
	public static final String YZ = "4";
	/**消息类型   公众端人民调解 状态更新*/
	public static final String RMTJ = "5";
	/**消息类型  工作人员处理*/
	public static final String YZG = "6";
	/**消息类型     法律服务者援助申请处理*/
	public static final String YZF = "7";
	/**消息类型      法律服务者调解申请处理*/
	public static final String RMF = "8";
	
	
	/**
	 * 针对ios区分测试和生产环境 true：生产环境；false：测试环境
	 */
	public static final boolean apnsProduction = false;
	
	/**
	 * 登录 踢下线
	 */
	public static final String KICKOUTTYPE = "20";
	
	public static void main(String[] args) {
//		ArrayList<String> list = new ArrayList<String>();
//		list.add("13558694652");
//		SendOneJpushAndroidAlias(GZ_MASTERSECRET, GZ_CUSKEY, "heheh", list, RMTJ, null, null);
         
	}
	/**
	 * 
	 *	方法名：SendOneJpushAndroid
	 *	@param ALERT 
	 *	@param TITLE 推送标题
	 *	@param content 推送内容
	 *	@param tag 设备号
	 *	返回类型：void
	 *	说明：安卓一对一推送（）
	 *	创建人：
	 * 	创建日期：2016年9月18日下午4:14:24
	 *	修改人：
	 *	修改日期：
	 */
	public static void SendOneJpushAndroid(String masterSecret,String cusKey, String content,String tag,String type,String logDate) {
		JPushClient jpushClient = new JPushClient(masterSecret, cusKey, 3);
		PushPayload payload = null ;
		
		if(StringUtils.isEmpty(content)){
			content="您有一条新的消息";
		 }
		
		
		payload = buildPushObject_all_tag_alertWithTitle(content, tag,type,logDate);
		try {
            PushResult result = jpushClient.sendPush(payload);
            logger.info("Got result - " + result);

        } catch (APIConnectionException e) {
            // Connection error, should retry later
        	logger.error("Connection error, should retry later", e);

        } catch (APIRequestException e) {
            // Should review the error, and fix the request
        	logger.error("Should review the error, and fix the request", e);
        	logger.info("HTTP Status: " + e.getStatus());
        	logger.info("Error Code: " + e.getErrorCode());
        	logger.info("Error Message: " + e.getErrorMessage());
        }
	}
	/**
	 * 
	 *	方法名：SendOneJpushAndroidAlias
	 *	@param masterSecret 
	 *	@param cusKey
	 *	@param content 推送内容
	 *	@param tag	账号list
	 *	返回类型：void
	 *	说明：账号推送
	 *	创建人：
	 * 	创建日期：2016年9月19日下午1:25:06
	 *	修改人：
	 *	修改日期：
	 */
	public static void SendOneJpushAndroidAlias(String masterSecret,String cusKey, String content,List<String> aliases,String type,Integer count,String reason) {
		JPushClient jpushClient = new JPushClient(masterSecret, cusKey, 3);
		PushPayload payload = null ;
		if(StringUtils.isEmpty(content)){
			content="您有一条新的消息";
		}
		if (count!=null && !StringUtils.isEmpty(reason)) {
			payload = buildPushObject_all_alias_alertWithTitleJB(content, aliases, type, count, reason);//设置推送消息
		}else {
			payload = buildPushObject_all_alias_alertWithTitle(content, aliases, type);
		}
		
		try {
			PushResult result = jpushClient.sendPush(payload);
			logger.info("Got result - " + result);
			
		} catch (APIConnectionException e) {
			// Connection error, should retry later
			logger.error("Connection error, should retry later", e);
			
		} catch (APIRequestException e) {
			// Should review the error, and fix the request
			logger.error("Should review the error, and fix the request", e);
			logger.info("HTTP Status: " + e.getStatus());
			logger.info("Error Code: " + e.getErrorCode());
			logger.info("Error Message: " + e.getErrorMessage());
		}
	}
	
	 /**
	  * 按设备推送所有平台
	  */
	 public static PushPayload buildPushObject_all_tag_alertWithTitle(String content,String tag,String type,String logDate) {
		 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 Date date = new Date();
		 String formatTime = formatter.format(date);
		 return PushPayload.newBuilder()
				 .setPlatform(Platform.android_ios())
				 .setAudience(Audience.registrationId(tag))
				 .setNotification(Notification.newBuilder()  
//	                        .setAlert(content)  
//	                        .addPlatformNotification(AndroidNotification.newBuilder()  
//	                                .addExtra("type", type).addExtra("time", formatTime)
//	                                .build())  
	                        .addPlatformNotification(IosNotification.newBuilder()  
	                        		.addExtra("type", type).addExtra("time", formatTime).addExtra("logDate", logDate)
	                        		.setAlert(content)
	                                .build())  
	                        .build()) 
				 .setOptions(Options.newBuilder()
                         .setApnsProduction(apnsProduction)
                         .build())
                  .setMessage(Message.newBuilder()
                		  .setMsgContent(content).addExtra("type", type).addExtra("time", formatTime).addExtra("logDate", logDate)
                		  .build())
				 .build();
	    }
	 /**
	  * 按账号推送所有平台(举报)
	  */
	 public static PushPayload buildPushObject_all_alias_alertWithTitleJB(String content,List<String> aliases,String type,Integer count,String reason) {
		 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 Date date = new Date();
		 String formatTime = formatter.format(date);
		 return PushPayload.newBuilder()
				 .setPlatform(Platform.android_ios())
				 .setAudience(Audience.alias(aliases))
				 .setNotification(Notification.newBuilder()  
//	                        .setAlert(content)  
//	                        .addPlatformNotification(AndroidNotification.newBuilder()  
//	                                .addExtra("reason", reason).addExtra("count", count).addExtra("time", formatTime).addExtra("type", type)
//	                                .build())  
	                        .addPlatformNotification(IosNotification.newBuilder()  
	                                .addExtra("reason", reason).addExtra("count", count).addExtra("time", formatTime).addExtra("type", type)
	                                .setAlert(content)
	                                .build())  
	                        .build()) 
				 .setOptions(Options.newBuilder()
                         .setApnsProduction(apnsProduction)
                         .build())
                  .setMessage(Message.newBuilder()
                		  .setMsgContent(content)
                		  .addExtra("reason", reason).addExtra("count", count).addExtra("time", formatTime).addExtra("type", type)
                		  .build())
				 .build();
	 }
	 /**
	  * 按账号推送所有平台
	  */
	 public static PushPayload buildPushObject_all_alias_alertWithTitle(String content,List<String> aliases,String type) {
		 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 Date date = new Date();
		 String formatTime = formatter.format(date);
		 return PushPayload.newBuilder()
				 .setPlatform(Platform.android_ios())
				 .setAudience(Audience.alias(aliases))
				 .setNotification(Notification.newBuilder()  
//	                        .setAlert(content)  
//	                        .addPlatformNotification(AndroidNotification.newBuilder()  
//	                                .addExtra("time", formatTime).addExtra("type", type)
//	                                .build())  
	                        .addPlatformNotification(IosNotification.newBuilder()  
	                                .addExtra("time", formatTime).addExtra("type", type)
	                                .setAlert(content)
	                                .build())  
	                        .build()) 
				 .setOptions(Options.newBuilder()
						 .setApnsProduction(apnsProduction)
						 .build())
						 .setMessage(Message.newBuilder()
								 .setMsgContent(content)
								 .addExtra("time", formatTime).addExtra("type", type)
								 .build())
								 .build();
	 }
	 /**
	  * ios按照账号
	  */
	 public static PushPayload buildPushObject_ios_alias_alertWithTitle(String content,List<String> aliases) {
		 return PushPayload.newBuilder()
				 .setPlatform(Platform.android_ios())
				 .setAudience(Audience.alias(aliases))
				 .setNotification(Notification.newBuilder()
						 .addPlatformNotification(IosNotification.newBuilder()
									 .setAlert(content)
									 .addExtra("df","dfa")
									 .build())
								 .build())
				 .setOptions(Options.newBuilder()
                         .setApnsProduction(apnsProduction)
                         .build())
                  .setMessage(Message.newBuilder().setMsgContent(content).build())
				 .build();
	 }
	 /**
	  * ios按照设备推送
	  */
	 public static PushPayload buildPushObject_ios_tag_alertWithTitle(String content,String tag) {
		 return PushPayload.newBuilder()
				 .setPlatform(Platform.android_ios())
				 .setAudience(Audience.registrationId(tag))
				 .setNotification(Notification.newBuilder()
						 .addPlatformNotification(IosNotification.newBuilder()
									 .setAlert(content)
									 .addExtra("df","dfa")
									 .build())
								 .build())
				 .setOptions(Options.newBuilder()
                         .setApnsProduction(apnsProduction)
                         .build())
                  .setMessage(Message.newBuilder().setMsgContent(content).build())
				 .build();
	 }
}
