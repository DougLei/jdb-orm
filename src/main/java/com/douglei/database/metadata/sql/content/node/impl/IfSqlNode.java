package com.douglei.database.metadata.sql.content.node.impl;

import java.util.Map;

import com.douglei.sessions.LocalRunDataHolder;
import com.douglei.utils.StringUtil;

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
	public boolean isMatching(Map<String, Object> sqlParameterMap) {
		if(StringUtil.isEmpty(expression)) {
			return true;
		}
		return LocalRunDataHolder.getExpressionResolverHandler().resolveToBoolean(expression, sqlParameterMap);
	}
}
