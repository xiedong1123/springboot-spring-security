package com.ccl.base.utils.cache;

import java.lang.reflect.Method;

import org.springframework.cache.interceptor.KeyGenerator;

/**
 * 实现spring cache的默认缓存实现策略
 * @author lisuo
 */
public class DefaultKeyGenerator implements KeyGenerator {
	@Override
	public Object generate(Object target, Method method, Object... params) {
		DefaultKey k = new DefaultKey(target, method, params);
		return k.getTargetClassName() + ":" + k.getMethodName() + ":" + k.hashCode();
	}
}
