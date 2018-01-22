package com.ccl.core.service;

import com.ccl.base.service.IBaseService;
import com.ccl.core.entity.SystemDictionary;

public interface ISystemDictionaryService extends IBaseService<SystemDictionary> {
	Integer getCodeCount(String code);

}
