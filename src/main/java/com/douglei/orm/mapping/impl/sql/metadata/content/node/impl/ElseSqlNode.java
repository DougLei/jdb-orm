package com.douglei.orm.mapping.impl.sql.metadata.content.node.impl;

import com.douglei.orm.mapping.impl.sql.metadata.content.node.SqlNodeType;

/**
 * 
 * @author DougLei
 */
public class ElseSqlNode extends AbstractSqlNode {
	private static final long serialVersionUID = 1956793892758331892L;

	public ElseSqlNode(String content) {
		super(content);
	}

	@Override
	public SqlNodeType getType() {
		return SqlNodeType.ELSE;
	}
}
