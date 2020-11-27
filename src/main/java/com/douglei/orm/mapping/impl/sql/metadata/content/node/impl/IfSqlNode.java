package com.douglei.orm.mapping.impl.sql.metadata.content.node.impl;

import com.douglei.orm.mapping.impl.sql.metadata.content.node.ExecuteSqlNode;
import com.douglei.orm.mapping.impl.sql.metadata.content.node.SqlNodeType;
import com.douglei.orm.mapping.metadata.validator.ValidationResult;
import com.douglei.orm.sessionfactory.sessions.session.sql.PurposeEntity;
import com.douglei.tools.instances.ognl.OgnlHandler;

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
	public ExecuteSqlNode getExecuteSqlNode(PurposeEntity purposeEntity, Object sqlParameter, String alias) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ValidationResult validateParameter(Object sqlParameter, String alias) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public SqlNodeType getType() {
		return SqlNodeType.IF;
	}
}
