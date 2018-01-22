package com.ccl.base.utils.page;

import java.io.Serializable;
import java.util.List;

/**
 * 
 *	类名称：PageSupport
 *	类描述：分页结果对象
 *	创建人：
 *	创建时间：2016-5-24上午10:53:07
 *	修改人：
 *	修改时间：
 *	修改备注：
 */
public class PageResult<T> implements Serializable{
	private static final long serialVersionUID = 1L;
	//private int total;		//总条数
	//private List<T> rows;	//结果集
	private int count;		//总条数
	private int pageCount;		//总页数
	private List<T> data;	//结果集
	private Integer pageNow =1;	//当前页
	
	private Integer code  = 0;
	private String msg;
	
	public Integer getPageNow() {
		return pageNow ;
	}

	public void setPageNow(Integer pageNow) {
		this.pageNow = pageNow;
	}
	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	
/*
	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}*/
}
