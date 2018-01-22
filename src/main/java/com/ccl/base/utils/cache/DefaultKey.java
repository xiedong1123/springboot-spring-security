package com.ccl.base.utils.cache;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Arrays;
import org.apache.commons.lang3.ArrayUtils;

/**
 * Spring cache key 生成策略 类名+方法名+参数信息 如果key的hashCode与equals一致,认为是同一个Key
 * 如果传入对象是BaseModel,那么便利它所有的一级属性,如果所有一级属性的hashCode一致, 则认为Key相同
 * @author lisuo
 */
public class DefaultKey implements Serializable {
	private static final long serialVersionUID = 1930236297081366076L;
	/** 调用目标对象全类名 */
	private String targetClassName;
	/** 调用目标方法名称 */
	private String methodName;
	/** 调用目标参数 */
	private Object[] params;
	private final int hashCode;

	public String getTargetClassName() {
		return targetClassName;
	}
	public void setTargetClassName(String targetClassName) {
		this.targetClassName = targetClassName;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public DefaultKey(Object target, Method method, Object[] elements) {
		this.targetClassName = target.getClass().getName();
		this.methodName = generatorMethodName(method);
		if (ArrayUtils.isNotEmpty(elements)) {
			this.params = new Object[elements.length];
			for (int i = 0; i < elements.length; i++) {
				Object ele = elements[i];
				/*
				 * if(ele instanceof BaseModel){ this.params[i] = ReflectUtil.beanToMap(ele); }else{
				 */
				this.params[i] = ele;
				/* } */
			}
		}
		this.hashCode = generatorHashCode();
	}
	private String generatorMethodName(Method method) {
		StringBuilder builder = new StringBuilder(method.getName());
		Class<?>[] types = method.getParameterTypes();
		if (ArrayUtils.isNotEmpty(types)) {
			builder.append("(");
			for (Class<?> type : types) {
				String name = type.getName();
				builder.append(name + ",");
			}
			builder.append(")");
		}
		return builder.toString();
	}
	// 生成hashCode
	private int generatorHashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + hashCode;
		result = prime * result + ((methodName == null) ? 0 : methodName.hashCode());
		result = prime * result + Arrays.hashCode(params);
		result = prime * result + ((targetClassName == null) ? 0 : targetClassName.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		DefaultKey other = (DefaultKey) obj;
		if (hashCode != other.hashCode) {
			return false;
		}
		if (methodName == null) {
			if (other.methodName != null) {
				return false;
			}
		} else if (!methodName.equals(other.methodName)) {
			return false;
		}
		if (!Arrays.equals(params, other.params)) {
			return false;
			}
		if (targetClassName == null) {
			if (other.targetClassName != null) {
				return false;
				}
		} else if (!targetClassName.equals(other.targetClassName)) {
			return false;
			}
		return true;
	}
	@Override
	public final int hashCode() {
		return hashCode;
	}
}
