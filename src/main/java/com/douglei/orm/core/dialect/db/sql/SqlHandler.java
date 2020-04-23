package com.douglei.orm.core.dialect.db.sql;

import com.douglei.orm.core.sql.pagequery.PageSqlStatement;

/**
 * sql处理器
 * @author DougLei
 */
public interface SqlHandler {
	
	/**
	 * 是否需要提取order by子句, 目前仅sqlserver需要
	 * @return
	 */
	default boolean needExtractOrderByClause() {
		return false;
	}
	
	/**
	 * 获取成分页查询的sql语句
	 * @param pageNum 
	 * @param pageSize 
	 * @param statement
	 */
	String getPageQuerySql(int pageNum, int pageSize, PageSqlStatement statement);
}
