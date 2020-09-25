package com.douglei.orm.core.dialect.impl.oracle.db.sql;

import com.douglei.orm.core.dialect.db.sql.SqlStatementHandler;
import com.douglei.orm.core.metadata.table.Constraint;
import com.douglei.tools.utils.StringUtil;

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
		return "select count(1) from user_objects where object_name =?";
	}

	@Override
	public String queryTableExists() {
		return "select count(1) from user_objects where object_name =? and object_type = 'TABLE'";
	}

	@Override
	public String queryViewExists() {
		return "select count(1) from user_objects where object_name =? and object_type = 'VIEW'";
	}

	@Override
	public String queryProcExists() {
		return "select count(1) from user_objects where object_name =? and object_type = 'PROCEDURE'";
	}
	
	@Override
	public String queryViewScript() {
		return "select text_length, text from user_views where view_name=?";
	}
	
	@Override
	public String queryProcScript() {
		return "select text from user_source where type='PROCEDURE' and name=? order by line";
	}
	
	// --------------------------------------------------------------------------------------------
	// constraint
	// --------------------------------------------------------------------------------------------
	@Override
	protected String createDefaultValue(Constraint constraint) {
		StringBuilder tmpSql = new StringBuilder(100);
		tmpSql.append("alter table ").append(constraint.getTableName()).append(" modify ").append(constraint.getConstraintColumnNames());
		tmpSql.append(" default ").append(constraint.getDefaultValue());
		return tmpSql.toString();
	}

	@Override
	protected String dropDefaultValue(Constraint constraint) {
		if(StringUtil.isEmpty(constraint.getConstraintColumnNames())) {
			throw new NullPointerException("在oracle数据库中删除列的默认值时, 必须传入相应的列名");
		}
		StringBuilder tmpSql = new StringBuilder(100);
		tmpSql.append("alter table ").append(constraint.getTableName()).append(" modify ").append(constraint.getConstraintColumnNames());
		tmpSql.append(" default null");
		return tmpSql.toString();
	}
}
