package com.ccl.core.entity;

import java.util.Date;

import com.ccl.base.entity.BaseEntity;

/**
 * 
 * @ClassName：SystemDictionary
 * @Description：数据字典类型
 * @Author：xiedong
 * @Date：2017年12月20日下午4:06:23
 * @version：1.0.0
 */
public class SystemDictionary extends BaseEntity {
    //串行版本ID
    private static final long serialVersionUID = -1306640858247398103L;


    // 类型代号
    private String code;

    // 类型名称
    private String name;

    // 描述信息
    private String description;

    // 启用状态(0启用,1:禁用)
    private Integer status;

    private Date createTime;

    private Date updateTime;


    /** 
     * 获取 类型代号 sys_systemdictionary.code
     * @return 类型代号
     */
    public String getCode() {
        return code;
    }

    /** 
     * 设置 类型代号 sys_systemdictionary.code
     * @param code 类型代号
     */
    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    /** 
     * 获取 类型名称 sys_systemdictionary.name
     * @return 类型名称
     */
    public String getName() {
        return name;
    }

    /** 
     * 设置 类型名称 sys_systemdictionary.name
     * @param name 类型名称
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /** 
     * 获取 描述信息 sys_systemdictionary.description
     * @return 描述信息
     */
    public String getDescription() {
        return description;
    }

    /** 
     * 设置 描述信息 sys_systemdictionary.description
     * @param description 描述信息
     */
    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    /** 
     * 获取 启用状态(0启用,1:禁用) sys_systemdictionary.status
     * @return 启用状态(0启用,1:禁用)
     */
    public Integer getStatus() {
        return status;
    }

    /** 
     * 设置 启用状态(0启用,1:禁用) sys_systemdictionary.status
     * @param status 启用状态(0启用,1:禁用)
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /** 
     * 获取 sys_systemdictionary.create_time
     * @return sys_systemdictionary.create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /** 
     * 设置 sys_systemdictionary.create_time
     * @param createTime sys_systemdictionary.create_time
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /** 
     * 获取 sys_systemdictionary.update_time
     * @return sys_systemdictionary.update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /** 
     * 设置 sys_systemdictionary.update_time
     * @param updateTime sys_systemdictionary.update_time
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append(", id=").append(id);
        sb.append(", code=").append(code);
        sb.append(", name=").append(name);
        sb.append(", description=").append(description);
        sb.append(", status=").append(status);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append("]");
        return sb.toString();
    }
}