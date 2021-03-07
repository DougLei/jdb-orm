package com.douglei.orm.mapping.impl.sql.executor.content.node.impl;

import com.douglei.orm.mapping.impl.sql.SqlNodeType;

/**
 * 
 * @author DougLei
 */
public class SetSqlNodeExecutor extends TrimSqlNodeExecutor {

	@Override
	public SqlNodeType getType() {
		return SqlNodeType.SET;
	}
}
