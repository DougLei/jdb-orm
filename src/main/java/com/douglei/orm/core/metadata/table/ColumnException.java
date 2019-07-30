package com.douglei.orm.core.metadata.table;

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
