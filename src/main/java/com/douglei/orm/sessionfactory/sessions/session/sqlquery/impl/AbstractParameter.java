package com.douglei.orm.sessionfactory.sessions.session.sqlquery.impl;

import com.douglei.orm.mapping.impl.sqlquery.metadata.SqlQueryMetadata;

/**
 * 
 * @author DougLei
 */
public abstract class AbstractParameter {
	protected LogicalOperator next = LogicalOperator.AND; // 与下一个参数之间的逻辑运算符, 默认为and
	
	/**
	 * 设置与下一个条件之间的逻辑运算符
	 * @param next
	 * @return
	 */
	public AbstractParameter setNext(LogicalOperator next) {
		this.next = next;
		return this;
	}
	
	/**
	 * 装配sql
	 * @param entity
	 * @param metadata
	 * @return 装配sql后, 是否支持拼接逻辑运算符
	 */
	protected abstract boolean assembleSQL(ExecutableQuerySql entity, SqlQueryMetadata metadata);
}
