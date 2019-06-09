package com.douglei.orm.configuration.environment.mapping.table;

/**
 * 不存在主键异常
 * @author DougLei
 */
public class NotExistsPrimaryKeyException extends RuntimeException{
	private static final long serialVersionUID = 6158341487128447601L;

	public NotExistsPrimaryKeyException(String message) {
		super(message);
	}
}
