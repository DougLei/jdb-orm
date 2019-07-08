package com.douglei.orm.configuration.impl.xml.element.extconfiguration;

/**
 * <extConfiguration>元素重复异常
 * @author DougLei
 */
public class RepeatedExtConfigurationElementException extends RuntimeException{
	private static final long serialVersionUID = -4195554822446156579L;

	public RepeatedExtConfigurationElementException(String message) {
		super(message);
	}
}
