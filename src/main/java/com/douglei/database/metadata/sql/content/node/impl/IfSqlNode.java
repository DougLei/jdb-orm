package com.douglei.database.metadata.sql.content.node.impl;

import com.douglei.database.metadata.sql.content.node.SqlNodeType;
import com.douglei.instances.ognl.OgnlHandler;

/**
 * 
 * @author DougLei
 */
public class IfSqlNode extends AbstractSqlNode {
	private String expression;
	
	public IfSqlNode(String expression, String content) {
		super(content);
		this.expression = expression;
	}

	@Override
	public boolean matching(Object sqlParameter) {
		return OgnlHandler.singleInstance().getBooleanValue(expression, sqlParameter);
	}
	
	@Override
	public SqlNodeType getType() {
		return SqlNodeType.IF;
	}
}
