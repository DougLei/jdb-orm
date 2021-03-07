package com.douglei.orm.sql.statement;

import java.util.List;

import com.douglei.orm.configuration.OrmException;

/**
 * 
 * @author DougLei
 */
public class StatementExecutionException extends OrmException{
	
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
