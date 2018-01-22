package com.ccl.base.utils.mail;

import java.util.List;



public class MailInfo {
	// 设置服务器
	private  String valueSmtp;
	private  String mailPort;
	// 发件人用户名、密码
	private String sendUser;
	private String sendPwd; 
	
	public MailInfo() {}
	
	
	public MailInfo(String valueSmtp, String mailPort, String sendUser, String sendPwd) {
		this.valueSmtp = valueSmtp;
		this.mailPort = mailPort;
		this.sendUser = sendUser;
		this.sendPwd = sendPwd;
	}


	public String getValueSmtp() {
		return valueSmtp;
	}
	public void setValueSmtp(String valueSmtp) {
		this.valueSmtp = valueSmtp;
	}
	public String getMailPort() {
		return mailPort;
	}
	public void setMailPort(String mailPort) {
		this.mailPort = mailPort;
	}
	public String getSendUser() {
		return sendUser;
	}
	public void setSendUser(String sendUser) {
		this.sendUser = sendUser;
	}
	public String getSendPwd() {
		return sendPwd;
	}
	public void setSendPwd(String sendPwd) {
		this.sendPwd = sendPwd;
	}





	//--------------------------
	/**邮件标题*/
	private String headName; 
	/**邮件内容*/
	private String sendHtml;
	/**收件人*/
	private String receiveUsers;
	/**抄送人*/
	private String receiveUsersCC;
	/**秘密抄送人*/
	private String receiveUsersBCC;
	/**图片*/
	private String imgSrc;
	/**邮件附件*/
	private List<String> files;
	
	
	

	public MailInfo(String headName, String sendHtml, String receiveUsers,
			String receiveUsersCC, String receiveUsersBCC, String imgSrc,
			List<String> files) {
		this.headName = headName;
		this.sendHtml = sendHtml;
		this.receiveUsers = receiveUsers;
		this.receiveUsersCC = receiveUsersCC;
		this.receiveUsersBCC = receiveUsersBCC;
		this.imgSrc = imgSrc;
		this.files = files;
	}



	public String getHeadName() {
		return headName;
	}
	public void setHeadName(String headName) {
		this.headName = headName;
	}
	public String getSendHtml() {
		return sendHtml;
	}
	public void setSendHtml(String sendHtml) {
		this.sendHtml = sendHtml;
	}
	public String getReceiveUsers() {
		return receiveUsers;
	}
	public void setReceiveUsers(String receiveUsers) {
		this.receiveUsers = receiveUsers;
	}
	public String getReceiveUsersCC() {
		return receiveUsersCC;
	}
	public void setReceiveUsersCC(String receiveUsersCC) {
		this.receiveUsersCC = receiveUsersCC;
	}
	public String getReceiveUsersBCC() {
		return receiveUsersBCC;
	}
	public void setReceiveUsersBCC(String receiveUsersBCC) {
		this.receiveUsersBCC = receiveUsersBCC;
	}
	public String getImgSrc() {
		return imgSrc;
	}
	public void setImgSrc(String imgSrc) {
		this.imgSrc = imgSrc;
	}
	public List<String> getFiles() {
		return files;
	}
	public void setFiles(List<String> files) {
		this.files = files;
	}
	
	
	
	private Integer error;  // 错误编码
	private String msg;  //消息
	private Boolean success;  //是否成功
	private Object data;  //是否成功
	
	
	
	public MailInfo(Boolean success, String msg,Integer error,Object data) {
		this.error = error;
		this.msg = msg;
		this.success = success;
		this.data = data;
	}
	public MailInfo(Boolean success, String msg,Integer error) {
		this.error = error;
		this.msg = msg;
		this.success = success;
	}



	public Integer getError() {
		return error;
	}
	public void setError(Integer error) {
		this.error = error;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Boolean getSuccess() {
		return success;
	}
	public void setSuccess(Boolean success) {
		this.success = success;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
}
