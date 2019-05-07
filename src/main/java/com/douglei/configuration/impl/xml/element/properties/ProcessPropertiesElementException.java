package com.douglei.configuration.impl.xml.element.properties;

/**
 * 处理<properties>元素异常
 * @author DougLei
 */
public class ProcessPropertiesElementException extends RuntimeException{
	private static final long serialVersionUID = 7852974328894556978L;

	public ProcessPropertiesElementException() {
		super();
	}

	public ProcessPropertiesElementException(String message) {
		super(message);
	}

	public ProcessPropertiesElementException(String message, Throwable cause) {
		super(message, cause);
	}
}
