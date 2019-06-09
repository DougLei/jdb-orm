package com.douglei.orm.core.metadata.sql;

/**
 * 匹配sql parameter异常
 * @author DougLei
 */
public class MatchingSqlParameterException extends RuntimeException{
	private static final long serialVersionUID = 7266537303891726522L;

	public MatchingSqlParameterException() {
		super();
	}

	public MatchingSqlParameterException(String message) {
		super(message);
	}
}
