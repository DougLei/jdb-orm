package com.douglei.core.dialect.db.table.entity;

/**
 * 索引异常
 * @author DougLei
 */
public class IndexException extends RuntimeException{
	private static final long serialVersionUID = 945854757145513211L;

	public IndexException(String message) {
		super(message);
	}
}
