package com.douglei.core.metadata.sql.content.node.impl;

import com.douglei.core.metadata.sql.content.node.SqlNodeType;

/**
 * 
 * @author DougLei
 */
public class WhereSqlNode extends TrimSqlNode {
	
	public WhereSqlNode() {
		super("where", null, "and|or", null);
	}

	@Override
	public SqlNodeType getType() {
		return SqlNodeType.WHERE;
	}
}