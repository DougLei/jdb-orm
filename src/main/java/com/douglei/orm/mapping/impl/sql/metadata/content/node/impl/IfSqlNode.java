package com.douglei.orm.mapping.impl.sql.metadata.content.node.impl;

import com.douglei.orm.mapping.impl.sql.SqlNodeType;

/**
 * 
 * @author DougLei
 */
public class IfSqlNode extends AbstractNestingNode {
	private String expression;
	public IfSqlNode(String expression) {
		this.expression = expression;
	}
	
	@Override
	public SqlNodeType getType() {
		return SqlNodeType.IF;
	}

	public String getExpression() {
		return expression;
	}
}
