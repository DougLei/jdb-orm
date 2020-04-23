package com.douglei.orm.core.dialect.db.sql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.orm.core.sql.pagequery.PageSqlStatement;
import com.douglei.orm.core.sql.recursivequery.RecursiveSqlStatement;

/**
 * sql处理器
 * @author DougLei
 */
public abstract class SqlHandler {
	private static final Logger logger = LoggerFactory.getLogger(SqlHandler.class);
	
	/**
	 * 是否需要提取order by子句, 目前仅sqlserver需要
	 * @return
	 */
	public boolean needExtractOrderByClause() {
		return false;
	}
	
	/**
	 * 获取成分页查询的sql语句
	 * @param pageNum 
	 * @param pageSize 
	 * @param statement
	 */
	public abstract String getPageQuerySql(int pageNum, int pageSize, PageSqlStatement statement);
	
	/**
	 * 获取递归查询的sql语句
	 * @param statement
	 * @return
	 */
	public String getRecursiveSql(RecursiveSqlStatement statement) {
		StringBuilder recursiveQuerySql = statement.getRecursiveQuerySqlCache();
		if(recursiveQuerySql == null) {
			recursiveQuerySql = new StringBuilder(85 + statement.getWithClause().length() + statement.getSql().length() + statement.getOrderByClause()==null?0:statement.getOrderByClause().length());
			
			if(statement.getWithClause() != null)
				recursiveQuerySql.append(statement.getWithClause()).append(' ');
			recursiveQuerySql.append("SELECT JDB_ORM_RECURSIVE_QUERY_.* FROM (");
			recursiveQuerySql.append(statement.getSql());
			recursiveQuerySql.append(") JDB_ORM_RECURSIVE_QUERY_ WHERE ");
			
			statement.setRecursiveQuerySqlCache(recursiveQuerySql);
		}
			
		int parentValueListSize = statement.parentValueListSize();
		if(statement.parentValueExistNull()) {
			recursiveQuerySql.append(statement.getParentPkColumnName()).append(" IS NULL");
			
			if(parentValueListSize > 0) {
				recursiveQuerySql.append(" OR ");
			}
		}
		
		if(parentValueListSize > 0) {
			recursiveQuerySql.append(statement.getParentPkColumnName());
			if(parentValueListSize == 1) {
				recursiveQuerySql.append("=?");
			}else {
				recursiveQuerySql.append("IN (");
				for(int i=0;i<parentValueListSize;i++) {
					recursiveQuerySql.append('?');
					if(i < parentValueListSize-1)
						recursiveQuerySql.append(',');
				}
				recursiveQuerySql.append(')');
			}
		}
		
		if(statement.getOrderByClause() != null)
			recursiveQuerySql.append(' ').append(statement.getOrderByClause());
		if(logger.isDebugEnabled()) {
			logger.debug("{} 进行递归查询的sql语句为: {}", getClass().getName(), recursiveQuerySql);
		}
		return recursiveQuerySql.toString();
		
		
		
	}
}
