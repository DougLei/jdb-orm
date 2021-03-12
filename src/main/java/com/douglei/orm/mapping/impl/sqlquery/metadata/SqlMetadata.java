package com.douglei.orm.mapping.impl.sqlquery.metadata;

import com.douglei.orm.mapping.metadata.Metadata;

/**
 * 
 * @author DougLei
 */
public class SqlMetadata implements Metadata{
	private String sql; // sql语句
	private String withClause; // with子句
	
	public SqlMetadata(String sql, String withClause) {
		this.sql = sql;
		this.withClause = withClause;
	}

	/**
	 * 获取sql语句的总长度
	 * @return
	 */
	public int getTotalLength() {
		return (withClause==null?0:withClause.length()) + sql.length();
	}
	
	/**
	 * 获取sql语句
	 * @return
	 */
	public String getSql() {
		return sql;
	}
	
	/**
	 * 获取with子句
	 * @return
	 */
	public String getWithClause() {
		return withClause;
	}
	
	@Override
	public String toString() {
		return "[sql=" + sql + ", withClause=" + withClause + "]";
	}
}
