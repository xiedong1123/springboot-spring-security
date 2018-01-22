	 
package com.ccl.core.query;

import com.ccl.base.utils.StringUtil;
import com.ccl.base.utils.page.ObjectQuery;


/**
 * 
 * @ClassName：OpLogQuery
 * @Description：
 * @author：xiedong
 * @Date：2017年12月13日下午3:31:25
 * @version：1.0.0
 */

public class OpLogQuery extends ObjectQuery{
	
	private static final long serialVersionUID = 5965013400187753584L;
	
	/**
	 * 操作位置
	 */
	private String operatAddress;
	/**
	 * 开始时间
	 */
	private String startTime;
	/**
	 * 结束时间
	 */
	private String endTime;
	
	private String operatTime;
	
	//----------------------------------------------------------------
	private String column;     					//排序字段
	private String sort ="DESC";       					//desc/aes
	//-----------------------------------------------------------------------
	
	
	
	
	/**
	 * @return the operatAddress
	 */
	public String getOperatAddress() {
		return operatAddress;
	}
	/**
	 * @return the column
	 */
	public String getColumn() {
		return column;
	}
	/**
	 * @param column the column to set
	 */
	public void setColumn(String column) {
		this.column = StringUtil.camelToUnderline(column);;
	}
	/**
	 * @return the sort
	 */
	public String getSort() {
		return sort;
	}
	/**
	 * @param sort the sort to set
	 */
	public void setSort(String sort) {
		this.sort = sort;
	}
	/**
	 * @param operatAddress the operatAddress to set
	 */
	public void setOperatAddress(String operatAddress) {
		this.operatAddress = operatAddress.trim();
	}
	/**
	 * @return the startTime
	 */
	public String getStartTime() {
		return startTime;
	}
	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	/**
	 * @return the endTime
	 */
	public String getEndTime() {
		return endTime;
	}
	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getOperatTime() {
		return operatTime;
	}
	public void setOperatTime(String operatTime) {
		this.operatTime = operatTime;
	}
	
	

}
