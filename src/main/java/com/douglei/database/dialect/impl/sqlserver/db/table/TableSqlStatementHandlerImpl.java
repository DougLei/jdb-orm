package com.douglei.database.dialect.impl.sqlserver.db.table;

import com.douglei.database.dialect.db.table.TableSqlStatementHandler;
import com.douglei.database.metadata.table.column.extend.ColumnConstraint;

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
	
	@Override
	protected String defaultValueConstraintCreateSqlStatement(ColumnConstraint constraint) {
		StringBuilder tmpSql = new StringBuilder(100);
		tmpSql.append("alter table ").append(constraint.getTableName()).append(" add constraint ").append(constraint.getName());
		tmpSql.append(" default ").append(constraint.getDefaultValue());
		tmpSql.append(" for ").append(constraint.getColumnName());
		return tmpSql.toString();
	}
}
