package com.douglei.orm.mapping.impl.table.exception;

/**
 * 序列配置异常
 * @author DougLei
 */
public class SequenceConfigurationException extends RuntimeException{
	private static final long serialVersionUID = 6609623786453972620L;

	public SequenceConfigurationException(String message) {
		super(message);
	}
}
