/* https://github.com/orange1438 */
package com.ccl.core.entity;

import java.util.Date;
import java.util.List;

import com.ccl.base.entity.BaseEntity;
import com.ccl.base.utils.excel.ExcelAttribute;

/**
 * 系统管理员 sys_user
 * 
 * @author xiedong date:2017/12/04 16:41
 */
public class User extends BaseEntity {
	// 串行版本ID
	private static final long serialVersionUID = -6891064277068630565L;

	// 账号
	@ExcelAttribute(headName="账号")
    protected String username;

    // 密码
    protected String password;

    // 姓名
    @ExcelAttribute(headName="姓名")
    protected String name;

    // 手机号
    @ExcelAttribute(headName="手机号")
    protected String mobile;

    // 城市id  默认：0
    protected Long cityId;

    // 锁定状态(0:正常,1:锁定)  默认：0
    protected Byte isAccountLocked;

    // 启用状态(0:禁用,1:启用)  默认：1
    protected Byte isEnabled;

    // 描述信息
    protected String remark;

    // 创建时间
    @ExcelAttribute(headName="创建时间")
    protected Date createTime;

    // 修改时间
    protected Date updateTime;

	//关联对象属性
	protected String roleIds;
	protected String roleKeys;
	@ExcelAttribute(headName="角色")
	protected String roleNames;
	protected List<Role> roles;

	  /** 
     * 获取 账号 sys_user.username
     * @return 账号
     */
    public String getUsername() {
        return username;
    }

    /** 
     * 设置 账号 sys_user.username
     * @param username 账号
     */
    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    /** 
     * 获取 密码 sys_user.password
     * @return 密码
     */
    public String getPassword() {
        return password;
    }

    /** 
     * 设置 密码 sys_user.password
     * @param password 密码
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /** 
     * 获取 姓名 sys_user.name
     * @return 姓名
     */
    public String getName() {
        return name;
    }

    /** 
     * 设置 姓名 sys_user.name
     * @param name 姓名
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /** 
     * 获取 手机号 sys_user.mobile
     * @return 手机号
     */
    public String getMobile() {
        return mobile;
    }

    /** 
     * 设置 手机号 sys_user.mobile
     * @param mobile 手机号
     */
    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    /** 
     * 获取 城市id sys_user.city_id
     * @return 城市id
     */
    public Long getCityId() {
        return cityId;
    }

    /** 
     * 设置 城市id sys_user.city_id
     * @param cityId 城市id
     */
    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    /** 
     * 获取 锁定状态(0:正常,1:锁定) sys_user.is_account_locked
     * @return 锁定状态(0:正常,1:锁定)
     */
    public Byte getIsAccountLocked() {
        return isAccountLocked;
    }

    /** 
     * 设置 锁定状态(0:正常,1:锁定) sys_user.is_account_locked
     * @param isAccountLocked 锁定状态(0:正常,1:锁定)
     */
    public void setIsAccountLocked(Byte isAccountLocked) {
        this.isAccountLocked = isAccountLocked;
    }

    /** 
     * 获取 启用状态(0:禁用,1:启用) sys_user.is_enabled
     * @return 启用状态(0:禁用,1:启用)
     */
    public Byte getIsEnabled() {
        return isEnabled;
    }

    /** 
     * 设置 启用状态(0:禁用,1:启用) sys_user.is_enabled
     * @param isEnabled 启用状态(0:禁用,1:启用)
     */
    public void setIsEnabled(Byte isEnabled) {
        this.isEnabled = isEnabled;
    }

    /** 
     * 获取 描述信息 sys_user.remark
     * @return 描述信息
     */
    public String getRemark() {
        return remark;
    }

    /** 
     * 设置 描述信息 sys_user.remark
     * @param remark 描述信息
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /** 
     * 获取 创建时间 sys_user.create_time
     * @return 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /** 
     * 设置 创建时间 sys_user.create_time
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /** 
     * 获取 修改时间 sys_user.update_time
     * @return 修改时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /** 
     * 设置 修改时间 sys_user.update_time
     * @param updateTime 修改时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public String getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}

	public String getRoleKeys() {
		return roleKeys;
	}

	public void setRoleKeys(String roleKeys) {
		this.roleKeys = roleKeys;
	}

	public String getRoleNames() {
		return roleNames;
	}

	public void setRoleNames(String roleNames) {
		this.roleNames = roleNames;
	}
	

	public User(User user) {
		this.id = user.getId();
		this.username = user.getUsername();
		this.password = user.getPassword();
		this.name = user.getName();
		this.mobile = user.getMobile();
		this.cityId = user.getCityId();
		this.isEnabled = user.getIsEnabled();
		this.isAccountLocked = user.getIsAccountLocked();
		this.remark = user.getRemark();
		this.createTime = user.getCreateTime();
		this.updateTime = user.getUpdateTime();
		this.roleIds = user.getRoleIds();
		this.roleKeys = user.getRoleKeys();
		this.roleNames = user.getRoleNames();
		this.roles = user.getRoles();
	}

	public User() {
	}
	
	
	
}