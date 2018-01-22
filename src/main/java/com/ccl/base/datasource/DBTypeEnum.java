package com.ccl.base.datasource;

public enum DBTypeEnum {
	
	one("dataSource_one"), 
	two("dataSource_two");
	
	private String value;

	DBTypeEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}