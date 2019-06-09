package com.douglei.orm.core.metadata.sql.content.node.impl;

import com.douglei.orm.core.metadata.sql.content.node.SqlNodeType;
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
	public boolean matching(Object sqlParameter, String alias) {
		processExpression(alias);
		return OgnlHandler.singleInstance().getBooleanValue(expression, sqlParameter);
	}
	
	private boolean unProcessExpression = true;// 是否【没有】处理expression, 默认都没有处理
	private void processExpression(String alias) {
		if(unProcessExpression) {
			unProcessExpression = false;
			
			if(alias != null && expression.indexOf(alias+".") != -1) {
				expression = expression.replace(alias+".", "");
			}
		}
	}

	@Override
	public SqlNodeType getType() {
		return SqlNodeType.IF;
	}
}
