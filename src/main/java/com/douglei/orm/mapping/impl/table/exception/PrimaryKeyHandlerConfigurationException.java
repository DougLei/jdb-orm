package com.douglei.orm.mapping.impl.table.exception;

/**
 * 主键处理器配置异常
 * @author DougLei
 */
public class PrimaryKeyHandlerConfigurationException extends RuntimeException{
	private static final long serialVersionUID = 6086799596883058898L;

	public PrimaryKeyHandlerConfigurationException(String message) {
		super(message);
	}
}
