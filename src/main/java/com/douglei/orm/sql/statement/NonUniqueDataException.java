package com.douglei.orm.sql.statement;

/**
 * 非唯一数据异常
 * @author DougLei
 */
public class NonUniqueDataException extends StatementExecutionException{

	public NonUniqueDataException(String message) {
		super(message);
	}
}
