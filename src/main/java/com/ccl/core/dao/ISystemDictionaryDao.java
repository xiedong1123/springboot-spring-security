package com.ccl.core.dao;

import com.ccl.base.dao.IBaseDao;
import com.ccl.core.entity.SystemDictionary;

public interface ISystemDictionaryDao extends IBaseDao<SystemDictionary> {
	Integer getCodeCount(String code);

}
