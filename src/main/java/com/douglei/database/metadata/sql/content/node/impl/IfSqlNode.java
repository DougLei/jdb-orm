package com.douglei.database.metadata.sql.content.node.impl;

import java.util.Map;

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
	public boolean matching(Map<String, Object> sqlParameterMap) {
		return OgnlHandler.singleInstance().getBooleanValue(expression, sqlParameterMap);
	}
	
	@Override
	public SqlNodeType getType() {
		return SqlNodeType.IF;
	}
}
