package com.douglei.orm.mapping.impl.sql.metadata.content.node.impl;

import com.douglei.orm.mapping.impl.sql.metadata.parser.content.node.SqlNodeType;
import com.douglei.tools.instances.ognl.OgnlHandler;

/**
 * 
 * @author DougLei
 */
public class IfSqlNode extends AbstractNestingNode {
	private static final long serialVersionUID = 504149933956674908L;
	
	private String expression;
	private boolean expressionFlag; // 标识是否处理过expression
	public IfSqlNode(String expression) {
		this.expression = expression;
	}
	
	@Override
	public boolean matching(Object sqlParameter, String previousAlias) {
		if(sqlParameter == null)
			return false;
		
		if(previousAlias != null && !expressionFlag) {
			expressionFlag = true;
			if(expression.startsWith(previousAlias+'.'))
				expression = expression.substring(previousAlias.length()+1);
		}
		return OgnlHandler.getSingleton().getBooleanValue(expression, sqlParameter);
	}
	
	@Override
	public SqlNodeType getType() {
		return SqlNodeType.IF;
	}
}
