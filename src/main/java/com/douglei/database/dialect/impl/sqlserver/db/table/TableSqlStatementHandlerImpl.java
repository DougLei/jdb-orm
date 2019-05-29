package com.douglei.database.dialect.impl.sqlserver.db.table;

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
	private static final String tableExistsQuerySql = "select count(1) from sysobjects where id = object_id(?) and type = 'U'";
}
