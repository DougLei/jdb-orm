package com.douglei.orm.core.dialect.db.table.entity;

/**
 * 索引异常
 * @author DougLei
 */
public class IndexException extends RuntimeException{
	private static final long serialVersionUID = 3636317294839120442L;

	public IndexException(String message) {
		super(message);
	}
}
