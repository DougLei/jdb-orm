package com.douglei.orm.mapping.impl.sql.executor.content.node.impl;

import com.douglei.orm.mapping.impl.sql.SqlNodeType;
import com.douglei.orm.mapping.impl.sql.executor.content.node.SqlNodeExecutor;
import com.douglei.orm.mapping.impl.sql.metadata.content.node.impl.IfSqlNode;
import com.douglei.tools.OgnlUtil;

/**
 * 
 * @author DougLei
 */
public class IfSqlNodeExecutor extends AbstractNestingNodeExecutor<IfSqlNode> implements SqlNodeExecutor<IfSqlNode> {

	@Override
	public SqlNodeType getType() {
		return SqlNodeType.IF;
	}

	@Override
	public boolean matching(IfSqlNode currentSqlNode, Object sqlParameter, String previousAlias) {
		if(sqlParameter == null)
			return false;
		
		String expression = currentSqlNode.getExpression();
		if(previousAlias != null && expression.startsWith(previousAlias+'.')) 
			expression = expression.substring(previousAlias.length()+1);
		return OgnlUtil.getBooleanValue(expression, sqlParameter);
	}
}
