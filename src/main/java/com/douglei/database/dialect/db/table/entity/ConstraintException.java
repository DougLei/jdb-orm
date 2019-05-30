package com.douglei.database.dialect.db.table.entity;

/**
 * 约束异常
 * @author DougLei
 */
public class ConstraintException extends RuntimeException{
	private static final long serialVersionUID = -6590042005221865467L;

	public ConstraintException(String message) {
		super(message);
	}
}
