package com.douglei.orm.core.metadata.table;

/**
 * 索引异常
 * @author DougLei
 */
public class IndexException extends RuntimeException{
	private static final long serialVersionUID = -1207383002835624549L;

	public IndexException(String message) {
		super(message);
	}
}
