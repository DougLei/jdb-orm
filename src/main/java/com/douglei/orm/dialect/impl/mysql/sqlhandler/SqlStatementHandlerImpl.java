package com.douglei.orm.dialect.impl.mysql.sqlhandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.orm.dialect.sqlhandler.SqlStatementHandler;
import com.douglei.orm.mapping.impl.table.metadata.ConstraintMetadata;
import com.douglei.orm.sql.pagequery.PageSqlStatement;
import com.douglei.orm.sql.pagerecursivequery.PageRecursiveSqlStatement;
import com.douglei.tools.StringUtil;

/**
 * 
 * @author DougLei
 */
public class SqlStatementHandlerImpl extends SqlStatementHandler{
	private static final Logger logger = LoggerFactory.getLogger(SqlStatementHandlerImpl.class);
	
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
	public String queryProcedureScript() {
		return "show create procedure "; // 后面加上存储过程名
	}
	
	// --------------------------------------------------------------------------------------------
	// constraint
	// --------------------------------------------------------------------------------------------
	@Override
	protected String createDefaultValue(ConstraintMetadata constraint) {
		StringBuilder tmpSql = new StringBuilder(100);
		tmpSql.append("alter table ").append(constraint.getTableName()).append(" alter column ").append(constraint.getConstraintColumnNames());
		tmpSql.append(" set default ").append(constraint.getDefaultValue());
		return tmpSql.toString();
	}

	@Override
	protected String dropPrimaryKey(ConstraintMetadata constraint) {
		StringBuilder tmpSql = new StringBuilder(100);
		tmpSql.append("alter table ").append(constraint.getTableName()).append(" drop primary key ");
		return tmpSql.toString();
	}

	@Override
	protected String dropUnique(ConstraintMetadata constraint) {
		StringBuilder tmpSql = new StringBuilder(100);
		tmpSql.append("alter table ").append(constraint.getTableName()).append(" drop index ").append(constraint.getName());
		return tmpSql.toString();
	}

	@Override
	protected String dropDefaultValue(ConstraintMetadata constraint) {
		if(StringUtil.isEmpty(constraint.getConstraintColumnNames())) {
			throw new NullPointerException("在mysql数据库中删除列的默认值时, 必须传入相应的列名");
		}
		StringBuilder tmpSql = new StringBuilder(100);
		tmpSql.append("alter table ").append(constraint.getTableName()).append(" alter column ").append(constraint.getConstraintColumnNames());
		tmpSql.append(" set default null");
		return tmpSql.toString();
	}
	
	@Override
	protected String dropCheck(ConstraintMetadata constraint) {
		return dropCK_FK(constraint);
	}
	
	@Override
	protected String dropForeignKey(ConstraintMetadata constraint) {
		return dropCK_FK(constraint);
	}
	
	/**获取删除检查约束、外键约束的sql语句*/
	private String dropCK_FK(ConstraintMetadata constraint) {
		StringBuilder tmpSql = new StringBuilder(100);
		tmpSql.append("alter table ").append(constraint.getTableName()).append(" drop ").append(constraint.getConstraintType().getSqlStatement()).append(" ").append(constraint.getName());
		return tmpSql.toString();
	}
	
	// --------------------------------------------------------------------------------------------
	// sql拼装
	// --------------------------------------------------------------------------------------------
	@Override
	public String getPageQuerySql(int pageNum, int pageSize, PageSqlStatement statement) {
		StringBuilder pageQuerySql = new StringBuilder(180 + statement.length());
		if(statement.getWithClause() != null)
			pageQuerySql.append(statement.getWithClause()).append(' ');
		pageQuerySql.append("SELECT JDB_ORM_SECOND_QUERY_.* FROM (");
		pageQuerySql.append(statement.getSql());
		pageQuerySql.append(") JDB_ORM_SECOND_QUERY_");
		if(statement instanceof PageRecursiveSqlStatement) { // 分页递归查询
			pageQuerySql.append(" WHERE ");
			appendConditionSql2RecursiveSql(pageQuerySql, (PageRecursiveSqlStatement)statement);
		}
		pageQuerySql.append(" LIMIT ");
		pageQuerySql.append((pageNum-1)*pageSize);
		pageQuerySql.append(",");
		pageQuerySql.append(pageSize);
		if(logger.isDebugEnabled()) 
			logger.debug("{} 进行分页查询的sql语句为: {}", getClass().getName(), pageQuerySql);
		return pageQuerySql.toString();
	}
}
