package com.ccl.core.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.ccl.base.entity.BaseEntity;

/**
 * 
 * 类名称：Resource 类描述：资源 创建人： 创建时间：2016年11月8日下午5:53:25 修改人： 修改时间： 修改备注：
 */
public class Resource extends BaseEntity {
	private static final long serialVersionUID = 1L;
	// ---------------------基本信息--------------------------
	@Length(max = 10, message = "资源名称长度不能超过10个字符!")
	@NotEmpty(message = "资源名称不能为空!")
	private String name; // 资源名称

	@NotNull(message = "父级菜单不能为空!")
	private Long parentId; // 父级ID

	@Length(max = 20, min = 4, message = "唯一标识长度为4~20个字符!")
	@NotEmpty(message = "唯一标识不能为空!")
	private String key; // 资源标识（唯一标识）

	@NotEmpty(message = "菜单类型不能为空!")
	private String type; // 菜单类型（munu导航菜单、button菜单按钮）

	@NotEmpty(message = "URL地址不能为空!")
	private String url; // URL地址

	@NotNull(message = "等级不能为空!")
	private Integer level; // 等级

	private String icon; // 小图标

	private Byte enable; // '启用状态(0:禁用,1:启用)',

	private Date createTime; // 创建时间

	private Date updateTime; // 修改时间

	private String btnHtml; // 按钮html代码

	@Length(max = 5, message = "描述长度不能超过180个字符!")
	private String description; // 描述

	private Integer btnType; // 功能按钮类型（1.功能按钮、2.超链接）

	// ---------------------关联对象--------------------------
	private String childrenIds; // 子菜单Ids
	private List<Resource> children = new ArrayList<>(); // 子菜单（资源）对象

	private String parentName; // 父类菜单名称
	// -----------------------------------------------------------------------------------------------------
	private String roleIds;
	private String roleNames;
	private List<Role> roles;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
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

	public String getBtnHtml() {
		return btnHtml;
	}

	public void setBtnHtml(String btnHtml) {
		this.btnHtml = btnHtml;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getBtnType() {
		return btnType;
	}

	public void setBtnType(Integer btnType) {
		this.btnType = btnType;
	}

	public String getChildrenIds() {
		return childrenIds;
	}

	public void setChildrenIds(String childrenIds) {
		this.childrenIds = childrenIds;
	}

	public List<Resource> getChildren() {
		return children;
	}

	public void setChildren(List<Resource> children) {
		this.children = children;
	}

	public String getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}

	public String getRoleNames() {
		return roleNames;
	}

	public void setRoleNames(String roleNames) {
		this.roleNames = roleNames;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	
}
