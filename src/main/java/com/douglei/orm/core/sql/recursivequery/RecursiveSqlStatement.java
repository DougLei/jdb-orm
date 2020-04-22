package com.douglei.orm.core.sql.recursivequery;

import com.douglei.orm.core.sql.pagequery.PageSqlStatement;

/**
 *递归查询用的sql语句对象
 * @author DougLei
 */
public class RecursiveSqlStatement extends PageSqlStatement {

	public RecursiveSqlStatement(String originSql) {
		super(originSql);
	}
}
