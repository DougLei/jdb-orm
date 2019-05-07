package com.douglei.configuration.impl.xml.element.properties;

/**
 * <properties>元素重复异常
 * @author DougLei
 */
public class RepeatPropertiesElementException extends RuntimeException{
	private static final long serialVersionUID = 3210201890787801098L;

	public RepeatPropertiesElementException() {
		super();
	}

	public RepeatPropertiesElementException(String message) {
		super(message);
	}
}
