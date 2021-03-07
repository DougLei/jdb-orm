package com.douglei.orm.mapping.impl.sql.metadata.content.node.impl;

import com.douglei.orm.mapping.impl.sql.SqlNodeType;

/**
 * 
 * @author DougLei
 */
public class WhereSqlNode extends TrimSqlNode {

	public WhereSqlNode() {
		super("where", null, new String[] {"and", "or"}, null);
	}

	@Override
	public SqlNodeType getType() {
		return SqlNodeType.WHERE;
	}
}