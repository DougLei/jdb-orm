package com.douglei.core.sql.pagequery;

/**
 * 页数溢出异常
 * @author DougLei
 */
public class PageNumOutOfBoundsException extends RuntimeException{
	private static final long serialVersionUID = 2157148169053673909L;

	public PageNumOutOfBoundsException() {
		super();
	}

	public PageNumOutOfBoundsException(String message) {
		super(message);
	}
}
