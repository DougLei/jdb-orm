package com.douglei.orm.sql.statement;

/**
 * 非唯一数据异常
 * @author DougLei
 */
public class NonUniqueDataException extends RuntimeException{
	private static final long serialVersionUID = -4955992721004923076L;

	public NonUniqueDataException(String message) {
		super(message);
	}
}
