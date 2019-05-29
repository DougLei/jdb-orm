package com.douglei.database.dialect.impl.oracle.db.table;

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
	private static final String tableExistsQuerySql = "select count(1) from user_objects where object_name = ? and object_type = 'TABLE'";
	
	@Override
	protected void setDefaultValueConstraintCreateSqlStatement2StringBuilderParameter(ColumnConstraint constraint, StringBuilder sql) {
		sql.append("alter table ").append(constraint.getTableName()).append(" modify ").append(constraint.getColumnName());
		sql.append(" default ").append(constraint.getDefaultValue());
	}
}
