package com.douglei.orm.mapping.impl.sql.metadata.content.node.impl;

import com.douglei.orm.mapping.impl.sql.metadata.content.node.SqlNodeType;

/**
 * 
 * @author DougLei
 */
public class WhereSqlNode extends TrimSqlNode {
	private static final long serialVersionUID = -3497180513973923811L;

	public WhereSqlNode() {
		super("where", null, "and|or", null);
	}

	@Override
	public SqlNodeType getType() {
		return SqlNodeType.WHERE;
	}
}