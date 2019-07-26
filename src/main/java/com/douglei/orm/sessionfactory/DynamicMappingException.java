package com.douglei.orm.sessionfactory;

/**
 * 动态映射异常
 * @author DougLei
 */
public class DynamicMappingException extends RuntimeException {
	private static final long serialVersionUID = -8920790026226798906L;
	
	public DynamicMappingException(String message) {
		super(message);
	}
	public DynamicMappingException(String message, Throwable t) {
		super(message, t);
	}
}
