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
	public String queryTableExists() {
		return "select count(1) from sysobjects where id = object_id(?) and type='U'";
	}

	@Override
	public String queryViewExists() {
		return "select count(1) from sysobjects where id = object_id(?) and type='V'";
	}
	
	@Override
	public String queryProcExists() {
		return "select count(1) from sysobjects where id = object_id(?) and type='P'";
	}
	
	@Override
	public String queryViewScript() {
		return "select b.definition from sysobjects a left join sys.sql_modules b on a.id=b.object_id where a.type='V' and a.name=?";
	}
	
	@Override
	public String queryProcScript() {
		return "select b.definition from sysobjects a left join sys.sql_modules b on a.id=b.object_id where a.type='P' and a.name=?";
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
	protected String createDefaultValue(Constraint constraint) {
		StringBuilder tmpSql = new StringBuilder(100);
		tmpSql.append("alter table ").append(constraint.getTableName()).append(" add constraint ").append(constraint.getName());
		tmpSql.append(" default ").append(constraint.getDefaultValue());
		tmpSql.append(" for ").append(constraint.getConstraintColumnNames());
		return tmpSql.toString();
	}
}
