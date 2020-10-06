package com.douglei.orm.sql.pagerecursivequery;

import com.douglei.orm.dialect.sql.SqlStatementHandler;
import com.douglei.orm.sql.recursivequery.RecursiveSqlStatement;

/**
 * 分页递归用的sql语句对象
 * @author DougLei
 */
public class PageRecursiveSqlStatement extends RecursiveSqlStatement {

	public PageRecursiveSqlStatement(SqlStatementHandler sqlStatementHandler, String originSql, String pkColumnName, String parentPkColumnName, String childNodeName, Object parentValue) {
		super(sqlStatementHandler, originSql, pkColumnName, parentPkColumnName, childNodeName, parentValue);
	}
	
	@Override
	public String getCountSql() {
		StringBuilder countSql = new StringBuilder(50 + length());
		if(getWithClause() != null)
			countSql.append(getWithClause()).append(' ');
		countSql.append("SELECT COUNT(1) FROM (").append(sql).append(") JDB_ORM_QC_ WHERE ");
		sqlStatementHandler.appendConditionSql2RecursiveSql(countSql, this);
		return countSql.toString();
	}
	
	/**
	 * 获取分页递归查询sql
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public String getPageRecursiveQuerySql(int pageNum, int pageSize) {
		return sqlStatementHandler.getPageQuerySql(pageNum, pageSize, this);
	}
}
