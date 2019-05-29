package com.douglei.database.dialect.db.sql;

/**
 * sql处理器
 * @author DougLei
 */
public interface SqlHandler {
	
	/**
	 * 组装成分页查询的sql语句
	 * @param pageNum 
	 * @param pageSize 
	 * @param withClause
	 * @param sql
	 * @return
	 */
	String installPageQuerySql(int pageNum, int pageSize, String withClause, String sql);
}
