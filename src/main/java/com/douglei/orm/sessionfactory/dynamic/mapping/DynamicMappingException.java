package com.douglei.orm.sessionfactory.dynamic.mapping;

/**
 * 动态映射异常
 * @author DougLei
 */
public class DynamicMappingException extends RuntimeException {
	private static final long serialVersionUID = -6620909654992312973L;

	public DynamicMappingException(String message, Throwable t) {
		super(message, t);
	}
}
