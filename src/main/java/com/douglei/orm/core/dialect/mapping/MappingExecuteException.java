package com.douglei.orm.core.dialect.mapping;

/**
 * 操作mapping异常
 * @author DougLei
 */
public class MappingExecuteException extends Exception{
	private static final long serialVersionUID = -7778780395458164897L;

	public MappingExecuteException(String message, Throwable t) {
		super(message, t);
	}
}
