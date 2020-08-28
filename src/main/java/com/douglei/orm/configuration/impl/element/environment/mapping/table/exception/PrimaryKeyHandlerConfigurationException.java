package com.douglei.orm.configuration.impl.element.environment.mapping.table.exception;

/**
 * 主键处理器配置异常
 * @author DougLei
 */
public class PrimaryKeyHandlerConfigurationException extends RuntimeException{
	private static final long serialVersionUID = 5305700256126961694L;

	public PrimaryKeyHandlerConfigurationException(String message) {
		super(message);
	}
}
