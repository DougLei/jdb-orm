package com.douglei.orm.mapping.impl.sql.metadata.content.node.impl;

import com.douglei.orm.mapping.impl.sql.SqlNodeType;

/**
 * 
 * @author DougLei
 */
public class SetSqlNode extends TrimSqlNode {

	public SetSqlNode() {
		super("set", null, null, new String[] {","});
	}

	@Override
	public SqlNodeType getType() {
		return SqlNodeType.SET;
	}
}