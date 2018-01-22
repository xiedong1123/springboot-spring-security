package com.ccl.core.entity;

import java.util.Date;

import com.ccl.base.entity.BaseEntity;

/**
 * 
 * @ClassName：SystemDictionaryItem
 * @Description：字典明细
 * @Author：xiedong
 * @Date：2017年12月20日下午4:06:40
 * @version：1.0.0
 */
public class SystemDictionaryItem extends BaseEntity {
    //串行版本ID
    private static final long serialVersionUID = -9221015258290110249L;

    // 代码编号，来自sys_systemdictionary.code
    private String code;

    // 枚举_值
    private String value;

    // 枚举_名称
    private String name;

    // 描述信息
    private String description;

    private Date createTime;

    private Date updateTime;


    /** 
     * 获取 代码编号，来自sys_systemdictionary.code sys_systemdictionaryitem.code
     * @return 代码编号，来自sys_systemdictionary.code
     */
    public String getCode() {
        return code;
    }

    /** 
     * 设置 代码编号，来自sys_systemdictionary.code sys_systemdictionaryitem.code
     * @param code 代码编号，来自sys_systemdictionary.code
     */
    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    /** 
     * 获取 枚举_值 sys_systemdictionaryitem.value
     * @return 枚举_值
     */
    public String getValue() {
        return value;
    }

    /** 
     * 设置 枚举_值 sys_systemdictionaryitem.value
     * @param value 枚举_值
     */
    public void setValue(String value) {
        this.value = value == null ? null : value.trim();
    }

    /** 
     * 获取 枚举_名称 sys_systemdictionaryitem.name
     * @return 枚举_名称
     */
    public String getName() {
        return name;
    }

    /** 
     * 设置 枚举_名称 sys_systemdictionaryitem.name
     * @param name 枚举_名称
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /** 
     * 获取 描述信息 sys_systemdictionaryitem.description
     * @return 描述信息
     */
    public String getDescription() {
        return description;
    }

    /** 
     * 设置 描述信息 sys_systemdictionaryitem.description
     * @param description 描述信息
     */
    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    /** 
     * 获取 sys_systemdictionaryitem.create_time
     * @return sys_systemdictionaryitem.create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /** 
     * 设置 sys_systemdictionaryitem.create_time
     * @param createTime sys_systemdictionaryitem.create_time
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /** 
     * 获取 sys_systemdictionaryitem.update_time
     * @return sys_systemdictionaryitem.update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /** 
     * 设置 sys_systemdictionaryitem.update_time
     * @param updateTime sys_systemdictionaryitem.update_time
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
        sb.append(", value=").append(value);
        sb.append(", name=").append(name);
        sb.append(", description=").append(description);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append("]");
        return sb.toString();
    }
}