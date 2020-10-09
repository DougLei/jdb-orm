package com.douglei.orm.mapping.impl.sql.metadata.content.node.impl;

import com.douglei.orm.mapping.impl.sql.metadata.content.node.SqlNodeType;
import com.douglei.tools.instances.ognl.OgnlHandler;

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
	public boolean matching(Object sqlParameter, String sqlParameterNamePrefix) {
		processExpression(sqlParameterNamePrefix);
		return OgnlHandler.getSingleton().getBooleanValue(expression, sqlParameter);
	}
	
	private boolean unProcessExpression = true;// 是否【没有】处理expression, 默认没有处理
	private void processExpression(String sqlParameterNamePrefix) {
		if(unProcessExpression) {
			unProcessExpression = false;
			
			if(sqlParameterNamePrefix != null && expression.indexOf(sqlParameterNamePrefix+".") != -1) {
				expression = expression.replace(sqlParameterNamePrefix+".", "");
			}
		}
	}

	@Override
	public SqlNodeType getType() {
		return SqlNodeType.IF;
	}
}
