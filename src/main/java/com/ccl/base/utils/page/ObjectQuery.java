package com.ccl.base.utils.page;

import java.io.Serializable;

/**
 * 
 *	类名称：QueryObject
 *	类描述：查询对象
 *	创建人：
 *	创建时间：2016-5-24上午10:53:07
 *	修改人：
 *	修改时间：
 *	修改备注：
 */
public class ObjectQuery implements Serializable{
	private static final long serialVersionUID = 1L;
	private int limit = 10; //页面大小
	private int page = 1;   //第几页(当前页)
	private int pageOffset;//开始位置
	private Byte deleted = 0;//删除位(默认0)
	//-------
	//	private int rows = 10; //页面大小
	//	private int page = 1;  //第几页
	
	
/*	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}*/
/*	public int getPageOffset() {
		this.pageOffset = (this.page-1)*this.rows;
		return pageOffset;
	}*/
	public int getPageOffset() {
		this.pageOffset = (this.page-1)*this.limit;
		return pageOffset;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public void setPageOffset(int po) {
		this.pageOffset = po;
	}
	public Byte getDeleted() {
		return deleted;
	}
	public void setDeleted(Byte deleted) {
		this.deleted = deleted;
	}
	/*//默认key,默认值
	public final static String PAGE_SIZE = "rows"; 		        //页面大小
	public final static String PAGE_OFFSET = "page";            //第几页
	public final static int DEFAULT_PAGE_SIZE = 10;             //默认：页面大小
	public final static int DEFAULT_PAGE_OFFSET = 1;            //默认:第几页
	//前端数据
	private int pageSize;  		//页面大小
	private int pageNo ;		//第几页（前端计算：当前页+1）
	private int pageOffset;     //起始位置 =（当前页-1）*页面大小

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageOffset() {
		return pageOffset;
	}

	public void setPageOffset(int pageOffset) {
		this.pageOffset = pageOffset;
	}

	*//**
	 * 
	 *	方法名：initPageSupport
	 *	@param request
	 *	@return
	 *	返回类型：QueryObject
	 *	说明：初始化并返回分页QueryObject对象
	 *	创建人：
	 * 	创建日期：2016-6-1下午4:59:14
	 *	修改人：
	 *	修改日期：
	 *//*
	public static QueryObject initQueryObject(HttpServletRequest request) {
		QueryObject q = new QueryObject();
		//页面大小
		String pageSize = request.getParameter(QueryObject.PAGE_SIZE);
		q.setPageSize(StringUtils.isEmpty(pageSize)? QueryObject.DEFAULT_PAGE_SIZE : Integer.valueOf(pageSize).intValue());
		//第几页
		String needPage =  request.getParameter(QueryObject.PAGE_OFFSET);
		q.setPageNo(StringUtils.isEmpty(needPage)? QueryObject.DEFAULT_PAGE_OFFSET : Integer.valueOf(needPage));
		//起始位置
		q.setPageOffset(((q.getPageNo() - 1) * q.getPageSize()));
		return q;
	}*/
}
