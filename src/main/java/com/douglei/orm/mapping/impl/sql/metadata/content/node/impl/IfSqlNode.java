package com.douglei.orm.mapping.impl.sql.metadata.content.node.impl;

import com.douglei.orm.mapping.impl.sql.metadata.content.node.SqlNodeType;

/**
 * 
 * @author DougLei
 */
public class IfSqlNode extends AbstractNestingNode {
	private String expression;
	private boolean expressionFlag; // 标识是否处理过expression
	public IfSqlNode(String expression) {
		this.expression = expression;
	}
	
	@Override
	public SqlNodeType getType() {
		return SqlNodeType.IF;
	}
}
