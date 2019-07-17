package com.douglei.orm.context;

/**
 * 多个匹配import path异常
 * @author DougLei
 */
public class MulitMatchImportPathException extends RuntimeException{
	private static final long serialVersionUID = 3611503404207500366L;

	public MulitMatchImportPathException(String message) {
		super(message);
	}
}
