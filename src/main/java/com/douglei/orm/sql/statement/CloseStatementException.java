package com.douglei.orm.sql.statement;

import java.sql.Statement;

/**
 * 
 * @author DougLei
 */
public class CloseStatementException extends RuntimeException{
	private static final long serialVersionUID = -1253522878593144879L;

	public CloseStatementException(Statement statement, Throwable e) {
		super("关闭["+statement.getClass().getName()+"]时出现异常", e);
	}
}
