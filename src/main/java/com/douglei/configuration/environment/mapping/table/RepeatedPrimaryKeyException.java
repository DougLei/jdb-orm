package com.douglei.configuration.environment.mapping.table;

/**
 * 主键重复异常
 * @author DougLei
 */
public class RepeatedPrimaryKeyException extends RuntimeException{
	private static final long serialVersionUID = -889972003326196422L;

	public RepeatedPrimaryKeyException(String message) {
		super(message);
	}
}
