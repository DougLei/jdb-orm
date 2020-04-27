package com.douglei.orm.core.sql.pagequery;

import com.douglei.orm.core.dialect.db.sql.SqlHandler;
import com.douglei.orm.core.sql.SqlStatement;

/**
 * 分页查询用的sql语句对象
 * @author DougLei
 */
public class PageSqlStatement extends SqlStatement{

	public PageSqlStatement(SqlHandler sqlHandler, String originSql) {
		super(sqlHandler, originSql);
	}

	/**
	 * 获取查询总数量的sql语句
	 * @return
	 */
	public String getCountSql() {
		StringBuilder countSql = new StringBuilder(50 + length());
		if(getWithClause() != null)
			countSql.append(getWithClause()).append(' ');
		countSql.append("SELECT COUNT(1) FROM (").append(sql).append(") JDB_ORM_QC_");
		return countSql.toString();
		
	}
	
	/**
	 * 获取分页查询的sql语句
	 * @param pageNum 第几页
	 * @param pageSize 一页显示的数量
	 * @return
	 */
	public String getPageQuerySql(int pageNum, int pageSize) {
		return sqlHandler.getPageQuerySql(pageNum, pageSize, this);
	}
}
