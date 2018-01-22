package com.ccl.base.entity;

import java.io.Serializable;

/**
 * 
 * 类名称：BaseEntity 类描述：实体基类（基本属性） 创建人： 创建时间：2016年11月30日下午3:46:59 修改人： 修改时间： 修改备注：
 * 说明：所以entity类都必须继承此类
 */
public class BaseEntity implements Serializable {
	protected static final long serialVersionUID = 1L;
	protected Long id; // 主键ID

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
