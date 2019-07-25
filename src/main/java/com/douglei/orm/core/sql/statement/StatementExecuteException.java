package com.douglei.orm.core.sql.statement;

import java.util.List;

import com.douglei.tools.utils.Collections;

/**
 * 
 * @author DougLei
 */
public class StatementExecuteException extends RuntimeException{
	private static final long serialVersionUID = -8564587486678378014L;
	
	public StatementExecuteException(String sql, Throwable cause) {
		this(sql, null, cause);
	}
	public StatementExecuteException(String sql, List<Object> parameters, Throwable cause) {
		super("在执行sql=["+sql+"]语句" + (Collections.isEmpty(parameters)?"":" ,参数为=["+parameters+"], ") + "时出现异常", cause);
	}
}
