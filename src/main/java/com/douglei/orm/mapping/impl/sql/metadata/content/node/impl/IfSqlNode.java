package com.douglei.orm.mapping.impl.sql.metadata.content.node.impl;

import com.douglei.orm.mapping.impl.sql.metadata.content.node.SqlNodeType;
import com.douglei.tools.instances.ognl.OgnlHandler;

/**
 * 
 * @author DougLei
 */
public class IfSqlNode extends AbstractSqlNode {
	private static final long serialVersionUID = -3653851460984152044L;
	
	private String expression;
	
	public IfSqlNode(String expression, String content) {
		super(content);
		this.expression = expression;
	}

	@Override
	public boolean matching(Object sqlParameter, String alias) {
		processExpression(alias);
		if(sqlParameter == null)
			return false;
		return OgnlHandler.getSingleton().getBooleanValue(expression, sqlParameter);
	}
	
	private boolean flag;// 是否处理了expression, 默认没有处理
	private void processExpression(String alias) {
		if(!flag) {
			flag = true;
			if(alias != null && expression.indexOf(alias+".") != -1) 
				expression = expression.replace(alias+".", "");
		}
	}

	@Override
	public SqlNodeType getType() {
		return SqlNodeType.IF;
	}
}
