package com.ccl.base.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ccl.base.dao.IBaseDao;
import com.ccl.base.service.IBaseService;
import com.ccl.base.utils.page.ObjectQuery;
import com.ccl.base.utils.page.PageResult;

/**
 * 
 *	类名称：BaseServiceImpl
 *	类描述：服务实现基类（实现了CRUD基本方法）
 *	创建人：
 *	创建时间：2016年11月8日下午6:04:58
 *	修改人：
 *	修改时间：
 *	修改备注：
 *  说明：所有service实现都必须继承此类，若觉得不满足需求，@Override即可
 */
//@WebService
public class BaseServiceImpl<T> implements IBaseService<T> {
	
    @Autowired
    private IBaseDao<T> baseDao;
    /**
     * 
     *	方法名：save
     *	@return 
     *	@param entity
     *	返回类型：void
     *	说明：保存
     *	创建人：
     * 	创建日期：2016年11月8日下午6:07:48
     *	修改人：
     *	修改日期：
     */
    @Override
	public void save(T entity) throws Exception {
        baseDao.insert(entity);
    }
    /**
     * 
     *	方法名：update
     *	@return
     *  @param entity
     *	返回类型：void
     *	说明：修改
     *	创建人：
     * 	创建日期：2016年11月8日下午6:07:48
     *	修改人：
     *	修改日期：
     */
	@Override
	public void update(T entity) throws Exception {
		 baseDao.update(entity);
	}
	 /**
     * 
     *	方法名：deleteById
     *	@return
     *	@param id
     *	返回类型：void
     *	说明：删除
     *	创建人：
     * 	创建日期：2016年11月8日下午6:07:48
     *	修改人：
     *	修改日期：
     */
	@Override
	public void deleteById(Serializable id) throws Exception {
		baseDao.deleteById(id);
	}
	/**
     * 
     *	方法名：getById
     *	@return
     *	@param id
     *	返回类型：T
     *	说明：查询（ID）
     *	创建人：
     * 	创建日期：2016年11月8日下午6:07:48
     *	修改人：
     *	修改日期：
     */
	@Override
	public T getById(Serializable id) throws Exception {
		return baseDao.getById(id);
	}
	/**
     * 
     *	方法名：getByWhere
     *	@return
     *	@param params
     *	返回类型：List<T>
     *	说明：查询（Where）
     *	创建人：
     * 	创建日期：2016年11月8日下午6:07:48
     *	修改人：
     *	修改日期：
     */
	@Override
	public List<T> getByWhere(HashMap<String, Object> params)throws Exception {
		return baseDao.getByWhere(params);
	}
	/**
     * 
     *	方法名：getAll
     *	@return
     *	返回类型：List<T>
     *	说明：查询（ALL）
     *	创建人：
     * 	创建日期：2016年11月8日下午6:07:48
     *	修改人：
     *	修改日期：
     */
	@Override
	public List<T> getAll() throws Exception {
		return baseDao.getAll();
	}

 
	/**
	 * 
	 *	方法名：getPageList
	 *	@param listkey mybatis查询列表数据的sql key
	 *	@param countKey mybatis查询列表总条数的sql key
	 *	@param queryObject 查询对象
	 *	@return
	 *	返回类型：PageSupport<T>
	 *	说明：分页查询\一般查询（当queryObject为null,则作页面查询，就是一般结果集）
	 *	创建人：
	 * 	创建日期：2016-5-24上午10:48:53
	 *	修改人：
	 *	修改日期：
	 */
	@Override
	public final PageResult<T> findPage(ObjectQuery queryObject)throws Exception{
		PageResult<T> pageResult = new PageResult<>();
		//1)分页查询
		if (queryObject != null) {
			Integer totalRecord = 0;
			//查询总条数
			totalRecord = this.findCount(queryObject);
			if (totalRecord == null || totalRecord.intValue() <= 0){
				pageResult.setData(new ArrayList<T>());
				return pageResult;
			}
			//当前起始位置>总条数（查询出来），则起始位置-页面大小
			int startIndex = queryObject.getPageOffset();
			if (startIndex >= totalRecord) {
				startIndex = startIndex - queryObject.getLimit();
				queryObject.setPageOffset(startIndex);
			}
			//查询结果集
			//封装：结果集、总条数、当前页、总页数
			pageResult.setCount(totalRecord);
			int pageCount = totalRecord/queryObject.getLimit();
			if(totalRecord%queryObject.getLimit()>0){
				pageCount++;
			}
			pageResult.setPageCount(pageCount);
		}
		//2)一般查询（没有分页）
		List<T> resultList = this.findQuery(queryObject);
		pageResult.setPageNow(queryObject.getPage());//当前页
		pageResult.setData(resultList);
		return pageResult;
	}
	
	
    /**
     * 
     *	方法名：findQuery
     *	@param objectQuery
     *	@return 
     *	@throws Exception
     *	返回类型：List<T>
     *	说明：查询结果集 （分页查询基本实现，若自定义，@Override即可）
     *	创建人: 
     * 	创建日期：2016-7-13下午3:01:35
     *	修改人：
     *	修改日期：
     */
	public List<T> findQuery(ObjectQuery objectQuery)throws Exception{
		return baseDao.findQuery(objectQuery);
	};
	/**
	 * 
	 *	方法名：findCount
	 *	@param objectQuery
	 *	@return
	 *	@throws Exception
	 *	返回类型：Integer
	 *	说明：查询总条数 （分页查询基本实现，若自定义，@Override即可）
	 *	创建人：
	 * 	创建日期：2016-7-13下午3:02:31
	 *	修改人：
	 *	修改日期：
	 */
	@Override
	public Integer findCount(ObjectQuery objectQuery)throws Exception{
		return baseDao.findCount(objectQuery);
	}

}
