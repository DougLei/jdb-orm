package com.douglei.database.sql;

/**
 * 事物没有结束异常
 * @author DougLei
 */
public class TransactionNotFinishException extends RuntimeException{
	private static final long serialVersionUID = -1160297254049397303L;

	public TransactionNotFinishException() {
	}

	public TransactionNotFinishException(String message) {
		super(message);
	}
}
