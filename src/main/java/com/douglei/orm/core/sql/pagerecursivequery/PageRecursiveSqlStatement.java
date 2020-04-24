package com.douglei.orm.core.sql.pagerecursivequery;

import com.douglei.orm.core.dialect.db.sql.SqlHandler;
import com.douglei.orm.core.sql.recursivequery.RecursiveSqlStatement;

/**
 * 分页递归用的sql语句对象
 * @author DougLei
 */
public class PageRecursiveSqlStatement extends RecursiveSqlStatement {

	public PageRecursiveSqlStatement(SqlHandler sqlHandler, String originSql, String pkColumnName, String parentPkColumnName, String childNodeName, Object parentValue) {
		super(sqlHandler, originSql, pkColumnName, parentPkColumnName, childNodeName, parentValue);
	}
	
	@Override
	public String getCountSql() {
		StringBuilder countSql = new StringBuilder(50 + length());
		if(getWithClause() != null)
			countSql.append(getWithClause()).append(' ');
		countSql.append("SELECT COUNT(1) FROM (").append(sql).append(") JDB_ORM_QC_ WHERE ");
		sqlHandler.appendConditionSql2RecursiveSql(countSql, this);
		return countSql.toString();
	}
	
	/**
	 * 获取分页递归查询sql
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public String getPageRecursiveQuerySql(int pageNum, int pageSize) {
		return sqlHandler.getPageQuerySql(pageNum, pageSize, this);
	}
}
