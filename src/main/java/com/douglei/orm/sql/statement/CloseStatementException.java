package com.douglei.orm.sql.statement;

import java.sql.Statement;

/**
 * 
 * @author DougLei
 */
public class CloseStatementException extends RuntimeException{

	public CloseStatementException(Statement statement, Throwable e) {
		super("关闭["+statement.getClass().getName()+"]时出现异常", e);
	}
}
