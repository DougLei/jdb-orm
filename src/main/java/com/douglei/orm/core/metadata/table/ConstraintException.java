package com.douglei.orm.core.metadata.table;

/**
 * 约束异常
 * @author DougLei
 */
public class ConstraintException extends RuntimeException{
	private static final long serialVersionUID = 7465997983906994597L;

	public ConstraintException(String message) {
		super(message);
	}
}
