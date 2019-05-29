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
	protected String defaultValueConstraintCreateSqlStatement(ColumnConstraint constraint) {
		StringBuilder tmpSql = new StringBuilder(100);
		tmpSql.append("alter table ").append(constraint.getTableName()).append(" modify ").append(constraint.getColumnName());
		tmpSql.append(" default ").append(constraint.getDefaultValue());
		return tmpSql.toString();
	}
}
