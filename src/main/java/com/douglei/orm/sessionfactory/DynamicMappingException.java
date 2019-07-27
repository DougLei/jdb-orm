package com.douglei.orm.sessionfactory;

/**
 * 动态映射异常
 * @author DougLei
 */
public class DynamicMappingException extends RuntimeException {
	private static final long serialVersionUID = -6415337605909196775L;

	public DynamicMappingException(String message, Throwable t) {
		super(message, t);
	}
}
