package com.ccl.core.query;

import com.ccl.base.utils.page.ObjectQuery;

public class SystemDictionaryQuery extends ObjectQuery {
	private static final long serialVersionUID = 660675219207779760L;
	
	private String name;
	
	private String code;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
