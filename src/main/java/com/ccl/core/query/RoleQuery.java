package com.ccl.core.query;

import com.ccl.base.utils.StringUtil;
import com.ccl.base.utils.page.ObjectQuery;

/**
 * 
 * @ClassName：RoleQuery
 * @Description：角色
 * @Author：zhengjing
 * @Date：2017年5月24日下午4:03:55 @version：1.0.0
 */
public class RoleQuery extends ObjectQuery {
	private static final long serialVersionUID = -6515513819850480553L;
	// --------------------------------查询对象属性----------------------------
	private Long id;
	private String name; // 角色名称
	private String key;
	private String column = "create_time"; // 排序字段
	private String sort = "desc"; // desc/aes //desc/aes

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getColumn() {
		return column;
	}

	public void setColumn(String column) {
		this.column =  StringUtil.camelToUnderline(column);
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	// ------------------------------------------------------------------------

}
