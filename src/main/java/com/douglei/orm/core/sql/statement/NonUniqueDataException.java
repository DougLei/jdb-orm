package com.douglei.orm.core.sql.statement;

/**
 * 非唯一数据异常
 * @author DougLei
 */
public class NonUniqueDataException extends RuntimeException{
	private static final long serialVersionUID = -7616459822182388260L;

	public NonUniqueDataException() {
		super();
	}

	public NonUniqueDataException(String message) {
		super(message);
	}
}
