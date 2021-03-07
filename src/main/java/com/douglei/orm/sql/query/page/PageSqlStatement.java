package com.douglei.orm.sql.query.page;

import com.douglei.orm.dialect.sqlhandler.SqlStatementHandler;
import com.douglei.orm.sql.query.QuerySqlStatement;

/**
 * 分页查询用的sql语句对象
 * @author DougLei
 */
public class PageSqlStatement extends QuerySqlStatement{

	/**
	 * 
	 * @param querySQL
	 * @param extractOrderByClause 是否需要提取querySQL中最外层的order by子句
	 */
	public PageSqlStatement(String querySQL, boolean extractOrderByClause) {
		super(querySQL, extractOrderByClause);
	}

	/**
	 * 获取查询总数量的sql语句
	 * @return
	 */
	public String getCountSql() {
		StringBuilder countSql = new StringBuilder(50 + getTotalLength());
		if(getWithClause() != null)
			countSql.append(getWithClause()).append(' ');
		countSql.append("SELECT COUNT(1) FROM (").append(querySQL).append(") JDB_ORM_QC_");
		return countSql.toString();
		
	}
	
	/**
	 * 获取分页查询的sql语句
	 * @param sqlStatementHandler
	 * @param pageNum 第几页
	 * @param pageSize 一页显示的数量
	 * @return
	 */
	public String getPageQuerySql(SqlStatementHandler sqlStatementHandler, int pageNum, int pageSize) {
		return sqlStatementHandler.getPageQuerySql(pageNum, pageSize, this);
	}
}
