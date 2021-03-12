package com.douglei.orm.sql.query.page;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.orm.dialect.sqlhandler.SqlStatementHandler;
import com.douglei.orm.sql.query.QuerySqlStatement;

/**
 * 分页查询用的sql语句对象
 * @author DougLei
 */
public class PageSqlStatement extends QuerySqlStatement{
	private static final Logger logger = LoggerFactory.getLogger(PageSqlStatement.class);
	
	/**
	 * 
	 * @param querySQL
	 * @param extractOrderByClause 是否需要提取sql中(最外层的)order by子句
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
		countSql.append("SELECT COUNT(1) FROM (").append(sql).append(") JDB_ORM_QC_");
		
		logger.debug("进行分页查询前的count sql语句为: {}", countSql);	
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
