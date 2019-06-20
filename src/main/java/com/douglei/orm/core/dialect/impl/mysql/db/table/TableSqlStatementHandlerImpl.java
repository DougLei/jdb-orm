package com.douglei.orm.core.dialect.impl.mysql.db.table;

import com.douglei.orm.core.dialect.db.table.TableSqlStatementHandler;
import com.douglei.orm.core.dialect.db.table.entity.Constraint;
import com.douglei.tools.utils.StringUtil;

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
	private static final String tableExistsQuerySql = "select count(1) from information_schema.tables where table_schema = (select database()) and table_name = ?";
	
	// --------------------------------------------------------------------------------------------
	// constraint
	// --------------------------------------------------------------------------------------------
	@Override
	protected String defaultValueConstraintCreateSqlStatement(Constraint constraint) {
		StringBuilder tmpSql = new StringBuilder(100);
		tmpSql.append("alter table ").append(constraint.getTableName()).append(" alter column ").append(constraint.getConstraintColumnNames());
		tmpSql.append(" set default ").append(constraint.getDefaultValue());
		return tmpSql.toString();
	}

	@Override
	protected String primaryKeyConstraintDropSqlStatement(Constraint constraint) {
		StringBuilder tmpSql = new StringBuilder(100);
		tmpSql.append("alter table ").append(constraint.getTableName()).append(" drop primary key ");
		return tmpSql.toString();
	}

	@Override
	protected String uniqueConstraintDropSqlStatement(Constraint constraint) {
		StringBuilder tmpSql = new StringBuilder(100);
		tmpSql.append("alter table ").append(constraint.getTableName()).append(" drop index ").append(constraint.getName());
		return tmpSql.toString();
	}

	@Override
	protected String defaultValueConstraintDropSqlStatement(Constraint constraint) {
		if(StringUtil.isEmpty(constraint.getConstraintColumnNames())) {
			throw new NullPointerException("在mysql数据库中删除列的默认值时, 必须传入相应的列名");
		}
		StringBuilder tmpSql = new StringBuilder(100);
		tmpSql.append("alter table ").append(constraint.getTableName()).append(" alter column ").append(constraint.getConstraintColumnNames());
		tmpSql.append(" set default null");
		return tmpSql.toString();
	}
	
	@Override
	protected String checkConstraintDropSqlStatement(Constraint constraint) {
		return ck_fk_constraintDropSqlStatement(constraint);
	}
	
	@Override
	protected String foreignKeyConstraintDropSqlStatement(Constraint constraint) {
		return ck_fk_constraintDropSqlStatement(constraint);
	}
	
	/**获取删除检查约束、外键约束的sql语句*/
	private String ck_fk_constraintDropSqlStatement(Constraint constraint) {
		StringBuilder tmpSql = new StringBuilder(100);
		tmpSql.append("alter table ").append(constraint.getTableName()).append(" drop ").append(constraint.getConstraintType().getSqlStatement()).append(" ").append(constraint.getName());
		return tmpSql.toString();
	}
	
	// --------------------------------------------------------------------------------------------
	// index
	// --------------------------------------------------------------------------------------------
}
