package com.ccl.core.entity;

import java.util.Date;
import java.util.List;

import org.springframework.security.access.ConfigAttribute;

import com.ccl.base.entity.BaseEntity;

/**
 * 
 * @author ccl
 *
 */
public class Role extends BaseEntity implements ConfigAttribute {

	private static final long serialVersionUID = 6601338670124559952L;
	// ---------------------基本信息-----------------
	private String key; // 角色标识（唯一标识）
	private String name; // 角色名称
	private Byte enable; // '启用状态(0:不可见,1:可见)',
	private Date createTime; // 创建时间
	private Date updateTime; // 修改时间
	private String description; // 描述
	// ---------------------关联对象-----------------
	private String resourceIds; // 拥有的资源ID
	private String resourceNames; // 拥有的资源Name
	private List<Resource> resources; // 拥有的资源集合对象

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Byte getEnable() {
		return enable;
	}

	public void setEnable(Byte enable) {
		this.enable = enable;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getResourceIds() {
		return resourceIds;
	}

	public void setResourceIds(String resourceIds) {
		this.resourceIds = resourceIds;
	}

	public String getResourceNames() {
		return resourceNames;
	}

	public void setResourceNames(String resourceNames) {
		this.resourceNames = resourceNames;
	}

	public List<Resource> getResources() {
		return resources;
	}

	public void setResources(List<Resource> resources) {
		this.resources = resources;
	}

	@Override
	public String getAttribute() {
		return this.key;
	}

}
