package com.douglei.orm.core.dialect.impl.mysql.db.sql;

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
		return "select count(1) from (" + 
				"	select table_name name_ from information_schema.tables where table_schema = (select database())" + 
				"	union" + 
				"	select routine_name name_ from information_schema.routines where routine_schema = (select database())" + 
				") a where a.name_ = ?";
	}

	@Override
	public String queryTableExists() {
		return "select count(1) from information_schema.tables where table_schema = (select database()) and table_name = ? and table_type='BASE TABLE'";
	}

	@Override
	public String queryViewExists() {
		return "select count(1) from information_schema.tables where table_schema = (select database()) and table_name = ? and table_type='VIEW'";
	}
	
	@Override
	public String queryProcExists() {
		return "select count(1) from information_schema.routines where routine_schema = (select database()) and routine_type='PROCEDURE' and routine_name = ?";
	}
	
	@Override
	public String queryViewScript() {
		return "select view_definition from information_schema.VIEWS where table_schema = (select database()) and table_name = ? ";
	}
	
	@Override
	public String queryProcScript() {
		return "show create procedure "; // 后面加上存储过程名
	}
	
	// --------------------------------------------------------------------------------------------
	// constraint
	// --------------------------------------------------------------------------------------------
	@Override
	protected String createDefaultValue(Constraint constraint) {
		StringBuilder tmpSql = new StringBuilder(100);
		tmpSql.append("alter table ").append(constraint.getTableName()).append(" alter column ").append(constraint.getConstraintColumnNames());
		tmpSql.append(" set default ").append(constraint.getDefaultValue());
		return tmpSql.toString();
	}

	@Override
	protected String dropPrimaryKey(Constraint constraint) {
		StringBuilder tmpSql = new StringBuilder(100);
		tmpSql.append("alter table ").append(constraint.getTableName()).append(" drop primary key ");
		return tmpSql.toString();
	}

	@Override
	protected String dropUnique(Constraint constraint) {
		StringBuilder tmpSql = new StringBuilder(100);
		tmpSql.append("alter table ").append(constraint.getTableName()).append(" drop index ").append(constraint.getName());
		return tmpSql.toString();
	}

	@Override
	protected String dropDefaultValue(Constraint constraint) {
		if(StringUtil.isEmpty(constraint.getConstraintColumnNames())) {
			throw new NullPointerException("在mysql数据库中删除列的默认值时, 必须传入相应的列名");
		}
		StringBuilder tmpSql = new StringBuilder(100);
		tmpSql.append("alter table ").append(constraint.getTableName()).append(" alter column ").append(constraint.getConstraintColumnNames());
		tmpSql.append(" set default null");
		return tmpSql.toString();
	}
	
	@Override
	protected String dropCheck(Constraint constraint) {
		return dropCK_FK(constraint);
	}
	
	@Override
	protected String dropForeignKey(Constraint constraint) {
		return dropCK_FK(constraint);
	}
	
	/**获取删除检查约束、外键约束的sql语句*/
	private String dropCK_FK(Constraint constraint) {
		StringBuilder tmpSql = new StringBuilder(100);
		tmpSql.append("alter table ").append(constraint.getTableName()).append(" drop ").append(constraint.getConstraintType().getSqlStatement()).append(" ").append(constraint.getName());
		return tmpSql.toString();
	}
}
