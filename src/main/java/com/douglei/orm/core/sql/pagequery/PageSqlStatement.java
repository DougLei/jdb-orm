package com.douglei.orm.core.sql.pagequery;

import com.douglei.orm.core.sql.SqlStatement;

/**
 * 分页查询用的sql语句对象
 * @author DougLei
 */
public class PageSqlStatement extends SqlStatement{

	public PageSqlStatement(String originSql) {
		super(originSql);
	}
}
