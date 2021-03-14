package com.douglei.orm.sql.query.page;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.orm.dialect.sqlhandler.SqlStatementHandler;
import com.douglei.orm.sql.query.QuerySqlStatement;

/**
 * 
 * @author DougLei
 */
public class PageSqlStatement extends QuerySqlStatement{
	private static final Logger logger = LoggerFactory.getLogger(PageSqlStatement.class);
	
	/**
	 * 
	 * @param sql
	 * @param extractOrderByClause 是否需要提取sql中(最外层的)order by子句
	 */
	public PageSqlStatement(String sql, boolean extractOrderByClause) {
		super(sql, extractOrderByClause);
	}

	/**
	 * 获取查询总数量的sql语句
	 * @return
	 */
	public String getCountSql() {
		StringBuilder countSql = new StringBuilder(50 + getTotalLength());
		if(withClause != null)
			countSql.append(withClause).append(' ');
		countSql.append("SELECT COUNT(1) FROM (").append(sql).append(") JDB_ORM_QC_");
		
		logger.debug("分页查询前的count sql语句为: {}", countSql);	
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
		return sqlStatementHandler.getPageQuerySql(pageNum, pageSize, null, this);
	}
	
	/**
	 * 获取分页递归查询的sql语句
	 * @param sqlStatementHandler
	 * @param pageNum 第几页
	 * @param pageSize 一页显示的数量
	 * @param recursiveConditionSQL 递归的条件sql
	 * @return
	 */
	public String getPageQuerySql(SqlStatementHandler sqlStatementHandler, int pageNum, int pageSize, String recursiveConditionSQL) {
		return sqlStatementHandler.getPageQuerySql(pageNum, pageSize, recursiveConditionSQL, this);
	}
}
