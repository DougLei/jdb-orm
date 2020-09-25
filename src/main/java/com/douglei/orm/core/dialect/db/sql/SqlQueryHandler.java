package com.douglei.orm.core.dialect.db.sql;

import java.sql.SQLException;

/**
 * 
 * @author DougLei
 */
public abstract class SqlQueryHandler {
	protected SqlStatementHandler sqlStatementHandler;
	
	public SqlQueryHandler(SqlStatementHandler sqlStatementHandler) {
		this.sqlStatementHandler = sqlStatementHandler;
	}

	/**
	 * 查询view的脚本
	 * @param viewName
	 * @param connection
	 * @return
	 * @throws SQLException
	 */
	public abstract String queryViewScript(String viewName, SqlQueryConnection connection) throws SQLException;
	
	/**
	 * 查询proc的脚本
	 * @param procName
	 * @param connection
	 * @return
	 * @throws SQLException
	 */
	public abstract String queryProcScript(String procName, SqlQueryConnection connection) throws SQLException;
}
