package com.ccl.core.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccl.base.service.impl.BaseServiceImpl;
import com.ccl.core.dao.ISystemDictionaryDao;
import com.ccl.core.entity.SystemDictionary;
import com.ccl.core.service.ISystemDictionaryService;


@Service
public class SystemDictionaryServiceImpl extends BaseServiceImpl<SystemDictionary> implements ISystemDictionaryService {
	@Autowired
	private ISystemDictionaryDao systemDictionaryDao;
	@Override
	public Integer getCodeCount(String code) {
		Integer codeCount = systemDictionaryDao.getCodeCount(code);
		return codeCount;
	}


}
