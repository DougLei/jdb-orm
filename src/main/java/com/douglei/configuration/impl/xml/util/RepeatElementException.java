package com.douglei.configuration.impl.xml.util;

/**
 * <xxx>元素重复异常
 * @author DougLei
 */
public class RepeatElementException extends RuntimeException{
	private static final long serialVersionUID = 5062245427747542862L;

	public RepeatElementException(String message) {
		super(message);
	}
}
