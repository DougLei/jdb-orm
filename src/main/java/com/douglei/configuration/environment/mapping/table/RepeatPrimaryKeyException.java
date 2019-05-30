package com.douglei.configuration.environment.mapping.table;

/**
 * 主键重复异常
 * @author DougLei
 */
public class RepeatPrimaryKeyException extends RuntimeException{
	private static final long serialVersionUID = 6657796291690968913L;

	public RepeatPrimaryKeyException(String message) {
		super(message);
	}
}
