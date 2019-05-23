package com.douglei.database.metadata.sql.content.node.impl;

import com.douglei.database.metadata.sql.content.node.SqlNodeType;

/**
 * 
 * @author DougLei
 */
public class SetSqlNode extends TrimSqlNode {
	
	public SetSqlNode() {
		super("set", null, null, ",");
	}

	@Override
	public SqlNodeType getType() {
		return SqlNodeType.SET;
	}
}