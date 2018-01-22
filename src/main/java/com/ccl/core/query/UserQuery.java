package com.ccl.core.query;

import com.ccl.base.utils.StringUtil;
import com.ccl.base.utils.page.ObjectQuery;

/**
 * 
 * @ClassName：SysUserQuery
 * @Description：系统用户
 * @Author：zhengjing
 * @Date：2017年5月25日下午4:41:34 @version：1.0.0
 */
public class UserQuery extends ObjectQuery {

	private static final long serialVersionUID = -3817864020601888738L;
	// --------------------------------查询对象属性----------------------------
	private Long id; // ID
	private String username; // 登录账号
	private Long roleId; // 角色名称
	private Byte isEnabled = 1; // 角色名称
	private String column = "create_time"; // 排序字段
	private String sort = "desc"; // desc/aes

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public Byte getIsEnabled() {
		return isEnabled;
	}

	public void setIsEnabled(Byte isEnabled) {
		this.isEnabled = isEnabled;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getColumn() {
		return column;
	}

	public void setColumn(String column) {
		this.column = StringUtil.camelToUnderline(column);
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

}
