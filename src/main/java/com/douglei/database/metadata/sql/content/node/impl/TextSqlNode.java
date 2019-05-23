package com.douglei.database.metadata.sql.content.node.impl;

import java.util.Map;

import com.douglei.database.metadata.sql.content.node.SqlNodeType;

/**
 * 
 * @author DougLei
 */
public class TextSqlNode extends AbstractSqlNode {

	public TextSqlNode(String content) {
		super(content);
	}

	@Override
	public boolean matching(Map<String, Object> sqlParameterMap) {
		return true;
	}
	
	@Override
	public SqlNodeType getType() {
		return SqlNodeType.TEXT;
	}
}
