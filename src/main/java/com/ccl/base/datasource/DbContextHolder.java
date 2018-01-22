package com.ccl.base.datasource;

public class DbContextHolder {
	@SuppressWarnings("rawtypes")
	private static final ThreadLocal contextHolder = new ThreadLocal<>();

	/**
	 * 设置数据源
	 * 
	 * @param dbTypeEnum
	 */
	@SuppressWarnings("unchecked")
	public static void setDbType(DBTypeEnum dbTypeEnum) {
		contextHolder.set(dbTypeEnum.getValue());
	}

	/**
	 * 取得当前数据源
	 * 
	 * @return
	 */
	public static Object getDbType() {
		return contextHolder.get() == null ? DBTypeEnum.one.getValue() : contextHolder.get();
	}

	/**
	 * 清除上下文数据
	 */
	public static void clearDbType() {
		contextHolder.remove();
	}
}
