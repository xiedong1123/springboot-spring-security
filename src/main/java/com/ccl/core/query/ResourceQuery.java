package com.ccl.core.query;

import com.ccl.base.utils.StringUtil;
import com.ccl.base.utils.page.ObjectQuery;

/**
 * 
 * 类名称：ResourceQuery 类描述：资源 创建人： 创建时间：2016年12月13日上午9:48:01 修改人： 修改时间： 修改备注：
 */
public class ResourceQuery extends ObjectQuery {

	private static final long serialVersionUID = 6781500445925930793L;
	
	// --------------------------------查询对象属性----------------------------
	private String id; // 资源ID
	private String name; // 资源名称
	private String key; // 资源标识（唯一标识）

	private String column = "level"; // 排序字段
	private String sort = "asc"; // desc/asc
	// ------------------------------------------------------------------------

	public String getId() {
		return id;
	}

	public void setId(String id) {
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

}
