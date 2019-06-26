package com.douglei.orm.core.dialect.db.table.handler;

/**
 * 列修改异常
 * @author DougLei
 */
public class ColumnModifyException extends RuntimeException{
	private static final long serialVersionUID = -5409290334756396005L;

	public ColumnModifyException(String message) {
		super(message);
	}
}
