package com.douglei.orm.configuration.impl.xml.util;

/**
 * <xxx>元素重复异常
 * @author DougLei
 */
public class RepeatedElementException extends RuntimeException{
	private static final long serialVersionUID = -5484218761718996921L;

	public RepeatedElementException(String message) {
		super(message);
	}
}
