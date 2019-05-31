package com.douglei.configuration.impl.xml.element.properties;

/**
 * <properties>元素重复异常
 * @author DougLei
 */
public class RepeatedPropertiesElementException extends RuntimeException{
	private static final long serialVersionUID = 2907910913804790869L;

	public RepeatedPropertiesElementException(String message) {
		super(message);
	}
}
