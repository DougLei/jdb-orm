package com.douglei.orm.sessionfactory.dynamic.mapping;

/**
 * 动态映射异常
 * @author DougLei
 */
public class DynamicMappingException extends RuntimeException {
	private static final long serialVersionUID = -7161452333455835249L;
	
	public DynamicMappingException(String message) {
		super(message);
	}
	public DynamicMappingException(String message, Throwable t) {
		super(message, t);
	}
}
