package com.ccl.base.utils.mail;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SendmailUtil {
	
	protected Logger logger = LoggerFactory.getLogger(getClass());
	// 发件人用户名、密码
	private String SEND_USER = null;
	private String SEND_PWD = null;
	// 建立会话
	private MimeMessage message;
	private Session session;

	public SendmailUtil() {}
	
	
	
	/**
	 * 发送
	 * @param mailInfo
	 * 				模板对象
	 * @return
	 */
	public static MailInfo sendMail(MailInfo mailInfo){
		
		return new SendmailUtil(mailInfo).doSendHtmlEmail(mailInfo);
	}

	/**
	 * 
	 * @Description 初始化方法
	 * @param mailInfo
	 * 				模板对象
	 */
	public SendmailUtil(MailInfo mailInfo) {
		
		this.SEND_USER = mailInfo.getSendUser();
		this.SEND_PWD = mailInfo.getSendPwd();
		Properties props = System.getProperties();
		// 设置SMTP的主机
		props.setProperty("mail.smtp.host", mailInfo.getValueSmtp());
		//验证用户
		props.setProperty("mail.smtp.auth", "true");
		//端口
		props.setProperty("mail.smtp.port", mailInfo.getMailPort());
		// 安全协议  SSL验证
		props.setProperty("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
		//只处理SSL的连接, 对于非SSL的连接不做处理
		props.setProperty("mail.smtp.socketFactory.fallback", "false");
		//端口
		props.setProperty("mail.smtp.socketFactory.port",mailInfo.getMailPort());
		props.setProperty("mail.smtp.starttls.enable", "true");
		//协议
		props.setProperty("mail.transport.protocol", "smtp");

		session = Session.getDefaultInstance(props, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(SEND_USER, SEND_PWD);
			}
		});
		session.setDebug(true);
		message = new MimeMessage(session);
	}



	
	
	
	
	/**
	 * 
	 * 发送邮件
	 * 
	 */
	public MailInfo doSendHtmlEmail(MailInfo mailInfo) {
		try {
			// 发件人
			InternetAddress from = new InternetAddress(SEND_USER);
			message.setFrom(from);
			// 设置发送时间
			message.setSentDate(new Date());
			// 邮件标题
			message.setSubject(mailInfo.getHeadName());
			

			//-------------------------- 收件人START--------------------------------------------------
			if (StringUtils.isNotBlank(mailInfo.getReceiveUsers())) {
				List<InternetAddress> user = new ArrayList<InternetAddress>();// 不能使用string类型的类型，这样只能发送一个收件人
				String[] medians = mailInfo.getReceiveUsers().split(";");// 对输入的多个邮件进行分号;分割
				for (String median : medians) {
					user.add(new InternetAddress(median));
				}
				InternetAddress[] address = (InternetAddress[]) user.toArray(new InternetAddress[user.size()]);
				message.setRecipients(Message.RecipientType.TO, address);
			}else {
				logger.error("收件人为空");
				mailInfo = new MailInfo(false,"收件人为空",-1);
			}
			 
			//-------------------------- 收件人END--------------------------------------------------
			 
			//--------------------------- 抄送人START-------------------------------------------------
			if (StringUtils.isNotBlank(mailInfo.getReceiveUsersCC())) {
				List<InternetAddress> userCC = new ArrayList<InternetAddress>();// 不能使用string类型的类型，这样只能发送一个收件人
				String[] mediansCC = mailInfo.getReceiveUsersCC().split(";");// 对输入的多个邮件进行分号;分割
				for (String medianCC : mediansCC) {
					userCC.add(new InternetAddress(medianCC));
				}
				InternetAddress[] addressCC = (InternetAddress[]) userCC.toArray(new InternetAddress[userCC.size()]);
				message.setRecipients(Message.RecipientType.CC,addressCC);
			}
			
			//--------------------------- 抄送人END-------------------------------------------------
			
			//-------------------------- 秘密抄送人START-----------------------------------------------
			if (StringUtils.isNotBlank(mailInfo.getReceiveUsersBCC())) {
				List<InternetAddress> userBCC = new ArrayList<InternetAddress>();// 不能使用string类型的类型，这样只能发送一个收件人
				String[] mediansBCC = mailInfo.getReceiveUsersBCC().split(";");// 对输入的多个邮件进行分号;分割
				for (String medianBCC : mediansBCC) {
					userBCC.add(new InternetAddress(medianBCC));
				}
				InternetAddress[] addressBCC = (InternetAddress[]) userBCC.toArray(new InternetAddress[userBCC.size()]);
				message.setRecipients(Message.RecipientType.BCC,addressBCC);
			}

			//-------------------------- 秘密抄送人END-----------------------------------------------
			

			//---------------------------- 添加正文内容START ---------------------------------------------
			
//			String content = mailInfo.getSendHtml().toString();
			MimeMultipart multipart = new MimeMultipart();
			MimeBodyPart contentPart = new MimeBodyPart();
			contentPart.setText(mailInfo.getSendHtml());
			contentPart.setHeader("Content-Type", "text/html; charset=UTF-8");
			multipart.addBodyPart(contentPart);

			//---------------------------- 添加正文内容 END---------------------------------------------
			/* 添加图片 */
			/*
			 * if (mailInfo.getImgSrc()!=null) { 
			 * MimeBodyPart image = new MimeBodyPart(); 
			 * FileDataSource fileDataSource = new FileDataSource(mailInfo.getImgSrc()); 
			 * image.setDataHandler(new DataHandler(fileDataSource));
			 * image.setContentID(fileDataSource.getName());
			 * multipart.addBodyPart(image); }
			 */

			//------------------------------- 添加附件START------------------------------------------------ 
			// MimeMultipart multipart = new MimeMultipart();
			MimeBodyPart fileBody = null;
			List<String> files = mailInfo.getFiles();
			if (files != null) {
				for (String file : files) {
					fileBody = new MimeBodyPart();
					File usFile = new File(file);
					DataSource source = new FileDataSource(file);
					fileBody.setDataHandler(new DataHandler(source));
					fileBody.setFileName(MimeUtility.encodeText(usFile.getName()));
					multipart.addBodyPart(fileBody);
				}
			}
			
			//------------------------------- 添加附件END------------------------------------------------ 
			
			// 邮件内容,也可以使纯文本"text/plain"
			message.setContent(multipart);
			message.saveChanges();
			
			Transport transport = session.getTransport();
			// smtp验证，就是你用来发邮件的邮箱用户名密码
			transport.connect(mailInfo.getValueSmtp(), SEND_USER, SEND_PWD);
			// 发送
			//封装数据
			List<Object> recipients = new ArrayList<Object>();
			Address[] allRecipients = message.getAllRecipients();
			for (int i = 0; i < allRecipients.length; i++) {
				try {
					transport.sendMessage(message,new Address[] { allRecipients[i] });
				} catch (Exception e) {
					Address address = allRecipients[i];
					recipients.add(address);
					logger.error("发送失败:"+address, e);
					e.printStackTrace();
				}
			}
			transport.close();
			logger.debug("send success!-----成功!!!");
			mailInfo = new MailInfo(true,"发送成功",99,recipients);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("send loser,---------失败!!!", e);
			mailInfo = new MailInfo(false,"发送失败",-99);
		}
		return mailInfo;
	}
	
	
	
	public static void main(String[] args) {
		MailInfo mailInfo = new MailInfo();
		mailInfo.setSendUser("xied@meyacom.com");
		mailInfo.setSendPwd("Xie123456789");
		mailInfo.setValueSmtp("smtp.exmail.qq.com");
		mailInfo.setMailPort("465");
		mailInfo.setHeadName("rrr333rr");
		mailInfo.setSendHtml("xxxxx<br/>xxxxxx22");
		mailInfo.setReceiveUsers("dongxiess@qq.com;1@qq.com;545049376@qq.com");
//		mailInfo.setReceiveUsersCC("dongxiess@qq.com");
//		mailInfo.setReceiveUsersBCC("545049376@qq.com");
		// mailInfo.setImgSrc("F:\\IMG_2538.JPG");
		List<String> files = new ArrayList<String>();
		files.add("F:\\2.docx");
		 mailInfo.setFiles(files);
//		SendmailUtil se = new SendmailUtil(mailInfo);
		// SendmailUtil se = new
		// SendmailUtil("app@dzssf.gov.cn","Zs447069","smtp.exmail.qq.com","465");
		// SendmailUtil se = new
		// SendmailUtil("545049376@qq.com","unnvhpmyofqabdaj","smtp.qq.com","465");
		// SendmailUtil se = new
		// SendmailUtil("13699432987@163.com","liu13536003547","smtp.163.com","25");
//		se.doSendHtmlEmail(mailInfo);
		 SendmailUtil.sendMail(mailInfo);
	}

	// 描述正文和图片的关系
				// MimeMultipart imageBody = new MimeMultipart();
				// imageBody.addBodyPart(contentPart);
				// imageBody.addBodyPart(image);
				// imageBody.setSubType("related");
				//
				// //描述正文和附件的关系
				//
				// MimeBodyPart bp = new MimeBodyPart();
				// bp.setContent(imageBody);
				// multipart.addBodyPart(bp);
				// multipart.setSubType("mixed");
}