
	 
package com.ccl.core.entity;

import java.util.Date;

import com.ccl.base.entity.BaseEntity;


/**
 * 
 * @ClassName：OpLog
 * @Description：操作日志
 * @author：xiedong
 * @Date：2017年12月13日下午3:25:35
 * @version：1.0.0
 */

public class OpLog extends BaseEntity{
	


	private static final long serialVersionUID = 1L;
	/**
	 * 管理员ID
	 */
	private Long userId;
	/**
	 * 操作人员
	 */
	private String username;
	
	private String name;
	/**
	 * 操作位置
	 */
	private String operatAddress;
	/**
	 * 操作内容
	 */
	private String content;
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * 更新时间
	 */
	private Date updateTime;

	
	
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the operatAddress
	 */
	public String getOperatAddress() {
		return operatAddress;
	}

	/**
	 * @param operatAddress the operatAddress to set
	 */
	public void setOperatAddress(String operatAddress) {
		this.operatAddress = operatAddress;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return the createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the updateTime
	 */
	public Date getUpdateTime() {
		return updateTime;
	}

	/**
	 * @param updateTime the updateTime to set
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	/**
	 * 
	 */
	public OpLog() {
		super();
	}

	/**
	 * @param adminId
	 * @param operatAddress
	 * @param content
	 */
	public OpLog(Long userId, String operatAddress, String content) {
		super();
		this.userId = userId;
		this.operatAddress = operatAddress;
		this.content = content;
		this.createTime = new Date();
	}


	
	

}
