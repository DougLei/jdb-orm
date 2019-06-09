package com.douglei.orm.core.metadata.sql.content.node.impl;

import com.douglei.orm.core.metadata.sql.content.node.SqlNodeType;

/**
 * 
 * @author DougLei
 */
public class ElseSqlNode extends AbstractSqlNode {
	
	public ElseSqlNode(String content) {
		super(content);
	}

	@Override
	public SqlNodeType getType() {
		return SqlNodeType.ELSE;
	}
}
