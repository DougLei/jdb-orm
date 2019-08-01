package com.douglei.orm.core.dialect.impl.sqlserver.db.table;

import com.douglei.orm.core.dialect.db.table.TableSqlStatementHandler;
import com.douglei.orm.core.metadata.table.ColumnMetadata;
import com.douglei.orm.core.metadata.table.Constraint;

/**
 * 
 * @author DougLei
 */
public class TableSqlStatementHandlerImpl extends TableSqlStatementHandler{
	
	// --------------------------------------------------------------------------------------------
	// table
	// --------------------------------------------------------------------------------------------
	@Override
	public String tableExistsQueryPreparedSqlStatement() {
		return "select count(1) from sysobjects where id = object_id(?) and type = 'U'";
	}
	
	@Override
	protected String primaryKeySequenceSqlKeyword() {
		return "identity";
	}

	@Override
	public String tableRenameSqlStatement(String originTableName, String targetTableName) {
		StringBuilder tmpSql = new StringBuilder(80);
		tmpSql.append("exec sp_rename '").append(originTableName).append("','").append(targetTableName).append("'");
		return tmpSql.toString();
	}
	
	// --------------------------------------------------------------------------------------------
	// column
	// --------------------------------------------------------------------------------------------
	@Override
	public String columnRenameSqlStatement(String tableName, String originColumnName, String targetColumnName) {
		StringBuilder tmpSql = new StringBuilder(100);
		tmpSql.append("exec sp_rename '").append(tableName).append(".").append(originColumnName).append("','").append(targetColumnName).append("','column'");
		return tmpSql.toString();
	}
	
	@Override
	public String columnModifySqlStatement(String tableName, ColumnMetadata column) {
		StringBuilder tmpSql = new StringBuilder(100);
		tmpSql.append("alter table ").append(tableName).append(" alter column ").append(column.getName()).append(" ");
		tmpSql.append(column.getDBDataType().getDBType4SqlStatement(column.getLength(), column.getPrecision())).append(" ");
		if(!column.isNullabled()) {
			tmpSql.append("not null");
		}
		return tmpSql.toString();
	}
	
	// --------------------------------------------------------------------------------------------
	// constraint
	// --------------------------------------------------------------------------------------------
	@Override
	protected String defaultValueConstraintCreateSqlStatement(Constraint constraint) {
		StringBuilder tmpSql = new StringBuilder(100);
		tmpSql.append("alter table ").append(constraint.getTableName()).append(" add constraint ").append(constraint.getName());
		tmpSql.append(" default ").append(constraint.getDefaultValue());
		tmpSql.append(" for ").append(constraint.getConstraintColumnNames());
		return tmpSql.toString();
	}
	
	// --------------------------------------------------------------------------------------------
	// index
	// --------------------------------------------------------------------------------------------
}
