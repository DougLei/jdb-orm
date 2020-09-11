package com.douglei.orm.core.mapping;

/**
 * 操作mapping异常
 * @author DougLei
 */
public class MappingExecuteException extends Exception{
	private static final long serialVersionUID = 6920694440023409370L;

	public MappingExecuteException(String message, Throwable t) {
		super(message, t);
	}
}
