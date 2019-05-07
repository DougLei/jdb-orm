package com.douglei.configuration.impl.xml.util;

/**
 * 不存在<xxx>元素异常
 * @author DougLei
 */
public class NotExistsElementException extends RuntimeException{
	private static final long serialVersionUID = -2130087542793043911L;

	public NotExistsElementException() {
		super();
	}

	public NotExistsElementException(String message) {
		super(message);
	}
}
