package com.douglei.orm.mapping.impl.sql.metadata.content.node.impl;

import com.douglei.orm.mapping.impl.sql.metadata.content.node.SqlNodeType;

/**
 * 
 * @author DougLei
 */
public class SetSqlNode extends TrimSqlNode {
	private static final long serialVersionUID = 8757312288662542191L;

	public SetSqlNode() {
		super("set", null, null, ",");
	}

	@Override
	public SqlNodeType getType() {
		return SqlNodeType.SET;
	}
}