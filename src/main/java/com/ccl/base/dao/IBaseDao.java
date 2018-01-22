package com.ccl.base.dao;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import com.ccl.base.utils.page.ObjectQuery;


/**
 * 
 *	类名称：IBaseDao
 *	类描述：Dao基类（声明CRUD常用接口方法）
 *	创建人：
 *	创建时间：2016年11月8日下午5:40:20
 *	修改人：
 *	修改时间：
 *	修改备注：
 *  说明：所有DAO层都必须继承此类
 */
public interface IBaseDao<T> {
	//保存
	void insert(T entity) throws Exception;
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
	//查询（分页查询_结果集）
	List<T> findQuery(ObjectQuery objectQuery)throws Exception;
	//查询（分页查询_数量）
	Integer findCount(ObjectQuery objectQuery)throws Exception;
}
