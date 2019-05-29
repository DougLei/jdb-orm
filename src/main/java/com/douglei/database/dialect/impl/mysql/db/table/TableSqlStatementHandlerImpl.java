package com.douglei.database.dialect.impl.mysql.db.table;

import com.douglei.database.dialect.db.table.TableSqlStatementHandler;
import com.douglei.database.metadata.table.ColumnMetadata;
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
	private static final String tableExistsQuerySql = "select count(1) from information_schema.tables where table_schema = (select database()) and table_name = ?";
	
	@Override
	public String columnCreateSqlStatement(String tableName, ColumnMetadata column) {
		StringBuilder tmpSql = new StringBuilder(100);
		tmpSql.append("alter table ").append(tableName).append(" add column ").append(column.getName()).append(" ");
		tmpSql.append(column.getDataType().defaultDBDataType().getDBType4SqlStatement(column.getLength(), column.getPrecision())).append(" ");
		if(!column.isNullabled()) {
			tmpSql.append("not null");
		}
		return tmpSql.toString();
	}
	
	@Override
	protected void setDefaultValueConstraintCreateSqlStatement2StringBuilderParameter(ColumnConstraint constraint, StringBuilder sql) {
		sql.append("alter table ").append(constraint.getTableName()).append(" modify ").append(constraint.getColumnName());
		sql.append(" default ").append(constraint.getDefaultValue());
	}
}
