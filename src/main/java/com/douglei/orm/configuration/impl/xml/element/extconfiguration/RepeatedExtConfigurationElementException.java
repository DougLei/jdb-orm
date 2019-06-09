package com.douglei.orm.configuration.impl.xml.element.extconfiguration;

/**
 * <ext-configuration>元素重复异常
 * @author DougLei
 */
public class RepeatedExtConfigurationElementException extends RuntimeException{
	private static final long serialVersionUID = -2437206151671980031L;

	public RepeatedExtConfigurationElementException(String message) {
		super(message);
	}
}
