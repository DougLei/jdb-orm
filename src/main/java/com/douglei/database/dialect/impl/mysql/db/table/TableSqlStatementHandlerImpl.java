package com.douglei.database.dialect.impl.mysql.db.table;

import com.douglei.database.dialect.db.table.TableSqlStatementHandler;
import com.douglei.database.metadata.table.column.extend.ColumnConstraint;
import com.douglei.utils.StringUtil;

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
	protected String defaultValueConstraintCreateSqlStatement(ColumnConstraint constraint) {
		StringBuilder tmpSql = new StringBuilder(100);
		tmpSql.append("alter table ").append(constraint.getTableName()).append(" alter column ").append(constraint.getColumnName());
		tmpSql.append(" set default ").append(constraint.getDefaultValue());
		return tmpSql.toString();
	}

	@Override
	protected String defaultValueConstraintDropSqlStatement(ColumnConstraint constraint) {
		if(StringUtil.isEmpty(constraint.getColumnName())) {
			throw new NullPointerException("在mysql数据库中删除列的默认值时, 必须传入相应的列名");
		}
		StringBuilder tmpSql = new StringBuilder(100);
		tmpSql.append("alter table ").append(constraint.getTableName()).append(" alter column ").append(constraint.getColumnName());
		tmpSql.append(" set default null");
		return tmpSql.toString();
	}
}
