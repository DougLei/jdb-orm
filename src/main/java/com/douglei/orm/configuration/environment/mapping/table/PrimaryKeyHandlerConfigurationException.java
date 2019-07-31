package com.douglei.orm.configuration.environment.mapping.table;

/**
 * 主键处理器配置异常
 * @author DougLei
 */
public class PrimaryKeyHandlerConfigurationException extends RuntimeException{
	private static final long serialVersionUID = -8053882539511353451L;

	public PrimaryKeyHandlerConfigurationException(String message) {
		super(message);
	}
}
