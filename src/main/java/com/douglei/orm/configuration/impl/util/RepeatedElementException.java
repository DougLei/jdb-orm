package com.douglei.orm.configuration.impl.util;

/**
 * <xxx>元素重复异常
 * @author DougLei
 */
public class RepeatedElementException extends RuntimeException{
	private static final long serialVersionUID = -29654722202706543L;

	public RepeatedElementException(String message) {
		super(message);
	}
}
