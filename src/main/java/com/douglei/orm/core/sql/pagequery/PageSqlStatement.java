package com.douglei.orm.core.sql.pagequery;

import com.douglei.orm.core.sql.SqlStatement;

/**
 * 分页查询用的sql语句对象
 * @author DougLei
 */
public class PageSqlStatement extends SqlStatement{
	private String orderBySql;
	
	public PageSqlStatement(String originSql) {
		super(originSql);
	}
	
	/**
	 * 修改sql语句
	 * @param sql
	 */
	public void updateSql(String sql) {
		super.sql = sql;
	}
	
	public String getOrderBySql() {
		return orderBySql;
	}
	public void setOrderBySql(String orderBySql) {
		this.orderBySql = orderBySql;
	}
}
