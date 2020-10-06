package com.douglei.orm.dialect.impl.oracle.db.sql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.orm.dialect.sql.SqlStatementHandler;
import com.douglei.orm.mapping.impl.table.metadata.Constraint;
import com.douglei.orm.sql.pagequery.PageSqlStatement;
import com.douglei.orm.sql.pagerecursivequery.PageRecursiveSqlStatement;
import com.douglei.tools.utils.StringUtil;

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
	public String queryProcedureScript() {
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
	
	// --------------------------------------------------------------------------------------------
	// sql拼装
	// --------------------------------------------------------------------------------------------
	@Override
	public String getPageQuerySql(int pageNum, int pageSize, PageSqlStatement statement) {
		int maxIndex = pageNum*pageSize;
		
		StringBuilder pageQuerySql = new StringBuilder(300 + statement.length());
		if(statement.getWithClause() != null)
			pageQuerySql.append(statement.getWithClause()).append(' ');
		pageQuerySql.append("SELECT JDB_ORM_THIRD_QUERY_.* FROM (SELECT JDB_ORM_SECOND_QUERY_.*, ROWNUM RN FROM (");
		pageQuerySql.append(statement.getSql());
		pageQuerySql.append(") JDB_ORM_SECOND_QUERY_ WHERE ROWNUM <= ");
		pageQuerySql.append(maxIndex);
		if(statement instanceof PageRecursiveSqlStatement) { // 分页递归查询
			pageQuerySql.append(" AND ");
			appendConditionSql2RecursiveSql(pageQuerySql, (PageRecursiveSqlStatement)statement);
		}
		pageQuerySql.append(" ) JDB_ORM_THIRD_QUERY_ WHERE JDB_ORM_THIRD_QUERY_.RN > ");
		pageQuerySql.append(maxIndex-pageSize);
		if(logger.isDebugEnabled()) 
			logger.debug("{} 进行分页查询的sql语句为: {}", getClass().getName(), pageQuerySql);
		return pageQuerySql.toString();
	}
}
