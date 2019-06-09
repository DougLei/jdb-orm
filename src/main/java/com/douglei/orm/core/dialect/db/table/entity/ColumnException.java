package com.douglei.orm.core.dialect.db.table.entity;

/**
 * 列异常
 * @author DougLei
 */
public class ColumnException extends RuntimeException{
	private static final long serialVersionUID = -7880113402940567688L;

	public ColumnException(String message) {
		super(message);
	}
}
