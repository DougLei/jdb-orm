package com.douglei.orm.core.sql.statement;

/**
 * 
 * @author DougLei
 */
public class StatementIsClosedException extends StatementExecutionException{
	private static final long serialVersionUID = -1909132269819014965L;

	public StatementIsClosedException() {
		super("statement已经被关闭");
	}
}
