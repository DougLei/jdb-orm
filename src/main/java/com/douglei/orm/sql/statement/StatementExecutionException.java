package com.douglei.orm.sql.statement;

import java.util.List;

/**
 * 
 * @author DougLei
 */
public class StatementExecutionException extends Exception{
	private static final long serialVersionUID = 2087124488420345593L;
	
	public StatementExecutionException(String message) {
		super(message);
	}
	public StatementExecutionException(String sql, Throwable cause) {
		this(sql, null, cause);
	}
	public StatementExecutionException(String sql, List<Object> parameters, Throwable cause) {
		super("在执行sql=["+sql+"]语句" + (parameters==null || parameters.isEmpty()?"":" ,参数为=["+parameters+"], ") + "时出现异常, " + cause.getMessage(), cause);
	}
}
