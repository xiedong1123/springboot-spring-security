package com.ccl.base.utils.excel;
/**
 * 
 * @ClassName：ExcelDownLoadListParamBean
 * @Description：下载的分包参数实体
 * @Author：zhengjing
 * @Date：2017年5月20日上午10:34:51
 * @version：1.0.0
 */
public class ExcelDownLoadListParamBean {
	
	private Integer pageSize = 10000; //分页大小（默认查询10000条）
	private Integer pageNow;  //第几页
	private String fileName;  //需下载的文件名称
	private String scope;     //范围

	
	
	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getPageNow() {
		return pageNow;
	}

	public void setPageNow(Integer pageNow) {
		this.pageNow = pageNow;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	
}
