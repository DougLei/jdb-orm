package com.douglei.database.dialect.impl.oracle.db.table;

import com.douglei.database.dialect.db.table.TableSqlStatementHandler;

/**
 * 
 * @author DougLei
 */
public class TableSqlStatementHandlerImpl extends TableSqlStatementHandler{

	@Override
	public String tableExistsQueryPreparedSqlStatement() {
		return tableExistsQuerySql;
	}
	private static final String tableExistsQuerySql = "select count(1) from user_objects where object_name = ? and object_type = 'TABLE'";
}
