package com.douglei.orm.core.sql.statement;

import java.sql.Statement;

/**
 * 
 * @author DougLei
 */
public class CloseStatementException extends RuntimeException{
	private static final long serialVersionUID = -3633250138731478187L;

	public CloseStatementException(Statement statement, Throwable e) {
		super("关闭["+statement.getClass().getName()+"]时出现异常", e);
	}
}
