package com.douglei.configuration.environment.mapping.table;

/**
 * 不支持的constraint 配置异常
 * @author DougLei
 */
public class UnsupportConstraintConfigurationException extends RuntimeException{
	private static final long serialVersionUID = -5582300236668215386L;

	public UnsupportConstraintConfigurationException(String message) {
		super(message);
	}
}
