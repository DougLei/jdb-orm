package com.douglei.database.dialect.impl.oracle.db.table;

import com.douglei.database.dialect.db.table.TableSqlStatementHandler;
import com.douglei.database.metadata.table.column.extend.Constraint;
import com.douglei.utils.StringUtil;

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
		return tableExistsQuerySql;
	}
	private static final String tableExistsQuerySql = "select count(1) from user_objects where object_name = ? and object_type = 'TABLE'";
	
	// --------------------------------------------------------------------------------------------
	// constraint
	// --------------------------------------------------------------------------------------------
	@Override
	protected String defaultValueConstraintCreateSqlStatement(Constraint constraint) {
		StringBuilder tmpSql = new StringBuilder(100);
		tmpSql.append("alter table ").append(constraint.getTableName()).append(" modify ").append(constraint.getColumnName());
		tmpSql.append(" default ").append(constraint.getDefaultValue());
		return tmpSql.toString();
	}

	@Override
	protected String defaultValueConstraintDropSqlStatement(Constraint constraint) {
		if(StringUtil.isEmpty(constraint.getColumnName())) {
			throw new NullPointerException("在oracle数据库中删除列的默认值时, 必须传入相应的列名");
		}
		StringBuilder tmpSql = new StringBuilder(100);
		tmpSql.append("alter table ").append(constraint.getTableName()).append(" modify ").append(constraint.getColumnName());
		tmpSql.append(" default null");
		return tmpSql.toString();
	}
	
	// --------------------------------------------------------------------------------------------
	// index
	// --------------------------------------------------------------------------------------------
}
