package com.douglei.orm.core.sql.statement;

import java.util.List;

import com.douglei.tools.utils.CollectionUtil;

/**
 * 
 * @author DougLei
 */
public class StatementExecutionException extends Exception{
	private static final long serialVersionUID = 3056757543881539491L;
	
	public StatementExecutionException(String message) {
		super(message);
	}
	public StatementExecutionException(String sql, Throwable cause) {
		this(sql, null, cause);
	}
	public StatementExecutionException(String sql, List<Object> parameters, Throwable cause) {
		super("在执行sql=["+sql+"]语句" + (CollectionUtil.isEmpty(parameters)?"":" ,参数为=["+parameters+"], ") + "时出现异常, " + cause.getMessage(), cause);
	}
}
