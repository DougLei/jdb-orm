package com.douglei.orm.core.dialect.db.sql;

import com.douglei.orm.core.sql.pagequery.PageSqlStatement;

/**
 * sql处理器
 * @author DougLei
 */
public interface SqlHandler {
	
	/**
	 * 组装成分页查询的sql语句
	 * @param pageNum 
	 * @param pageSize 
	 * @param statement
	 * @return
	 */
	String installPageQuerySql(int pageNum, int pageSize, PageSqlStatement statement);
}
