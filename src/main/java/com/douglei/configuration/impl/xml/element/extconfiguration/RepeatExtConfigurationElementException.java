package com.douglei.configuration.impl.xml.element.extconfiguration;

/**
 * <ext-configuration>元素重复异常
 * @author DougLei
 */
public class RepeatExtConfigurationElementException extends RuntimeException{
	private static final long serialVersionUID = -6546710940914516591L;

	public RepeatExtConfigurationElementException() {
		super();
	}

	public RepeatExtConfigurationElementException(String message) {
		super(message);
	}
}
