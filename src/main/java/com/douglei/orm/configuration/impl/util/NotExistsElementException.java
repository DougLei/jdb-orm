package com.douglei.orm.configuration.impl.util;

/**
 * 不存在<xxx>元素异常
 * @author DougLei
 */
public class NotExistsElementException extends RuntimeException{
	private static final long serialVersionUID = -8375061312297868284L;

	public NotExistsElementException(String message) {
		super(message);
	}
}
