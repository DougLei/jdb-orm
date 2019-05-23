package com.douglei.database.metadata.sql.content.node.impl;

import java.util.Map;

import com.douglei.database.metadata.sql.content.node.SqlNodeType;

/**
 * 
 * @author DougLei
 */
public class ElseSqlNode extends AbstractSqlNode {
	
	public ElseSqlNode(String content) {
		super(content);
	}

	@Override
	public boolean matching(Map<String, Object> sqlParameterMap) {
		return true;
	}

	@Override
	public SqlNodeType getType() {
		return SqlNodeType.ELSE;
	}
}
