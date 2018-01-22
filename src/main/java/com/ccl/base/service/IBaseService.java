package com.ccl.base.service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import com.ccl.base.utils.page.ObjectQuery;
import com.ccl.base.utils.page.PageResult;


/**
 * 
 *	类名称：IBaseService
 *	类描述：Service基类（声明CRUD常用接口方法）
 *	创建人：
 *	创建时间：2016年11月8日下午5:40:20
 *	修改人：
 *	修改时间：
 *	修改备注：
 *  备注：所有service层接口都必须继承此类
 */
public interface IBaseService<T> {
	//保存
	void save(T entity) throws Exception;
	//修改
	void update(T entity)throws Exception;
	//删除
	void deleteById(Serializable id)throws Exception;
	//查询（ID）
	T getById(Serializable id)throws Exception;
	//查询（where）
	List<T> getByWhere(HashMap<String, Object> params)throws Exception;
	//查询（All）
	List<T> getAll()throws Exception;
	//查询(分页 查询)
	PageResult<T> findPage(ObjectQuery queryObject)throws Exception;
	//查询总条数
	public Integer findCount(ObjectQuery objectQuery)throws Exception;
}
