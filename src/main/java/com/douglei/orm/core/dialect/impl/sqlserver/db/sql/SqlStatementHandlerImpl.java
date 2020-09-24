package com.douglei.orm.core.dialect.impl.sqlserver.db.sql;

import com.douglei.orm.core.dialect.db.sql.SqlStatementHandler;
import com.douglei.orm.core.metadata.table.ColumnMetadata;
import com.douglei.orm.core.metadata.table.Constraint;

/**
 * 
 * @author DougLei
 */
public class SqlStatementHandlerImpl extends SqlStatementHandler{
	
	// --------------------------------------------------------------------------------------------
	// query
	// --------------------------------------------------------------------------------------------
	@Override
	public String queryNameExists() {
		return "select count(1) from sysobjects where id = object_id(?)";
	}

	@Override
	public String queryTableNameExists() {
		return "select count(1) from sysobjects where id = object_id(?) and type='U'";
	}

	@Override
	public String queryViewNameExists() {
		return "select count(1) from sysobjects where id = object_id(?) and type='V'";
	}

	@Override
	public String queryProcNameExists() {
		return "select count(1) from sysobjects where id = object_id(?) and type='P'";
	}
	
	// --------------------------------------------------------------------------------------------
	// table
	// --------------------------------------------------------------------------------------------
	@Override
	protected String primaryKeySequenceSqlKeyword() {
		return "identity";
	}

	@Override
	public String renameTable(String originTableName, String targetTableName) {
		StringBuilder tmpSql = new StringBuilder(80);
		tmpSql.append("exec sp_rename '").append(originTableName).append("','").append(targetTableName).append("'");
		return tmpSql.toString();
	}
	
	// --------------------------------------------------------------------------------------------
	// column
	// --------------------------------------------------------------------------------------------
	@Override
	public String renameColumn(String tableName, String originColumnName, String targetColumnName) {
		StringBuilder tmpSql = new StringBuilder(100);
		tmpSql.append("exec sp_rename '").append(tableName).append(".").append(originColumnName).append("','").append(targetColumnName).append("','column'");
		return tmpSql.toString();
	}
	
	@Override
	public String modifyColumn(String tableName, ColumnMetadata column) {
		StringBuilder tmpSql = new StringBuilder(100);
		tmpSql.append("alter table ").append(tableName).append(" alter column ").append(column.getName()).append(" ");
		tmpSql.append(column.getDBDataType().getDBType4SqlStatement(column.getLength(), column.getPrecision())).append(" ");
		if(!column.isNullable()) {
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
}
