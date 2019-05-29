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
	protected void setDefaultValueConstraintCreateSqlStatement2StringBuilderParameter(ColumnConstraint constraint, StringBuilder sql) {
		sql.append("alter table ").append(constraint.getTableName()).append(" add constraint ").append(constraint.getName());
		sql.append(" default ").append(constraint.getDefaultValue());
		sql.append(" for ").append(constraint.getColumnName());
	}
}
