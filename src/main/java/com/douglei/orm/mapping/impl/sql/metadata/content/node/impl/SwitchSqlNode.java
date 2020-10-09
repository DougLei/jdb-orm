package com.douglei.orm.mapping.impl.sql.metadata.content.node.impl;

import com.douglei.orm.mapping.impl.sql.metadata.content.node.ExecuteSqlNode;
import com.douglei.orm.mapping.impl.sql.metadata.content.node.SqlNode;
import com.douglei.orm.mapping.impl.sql.metadata.content.node.SqlNodeType;
import com.douglei.orm.mapping.metadata.validator.ValidationResult;

/**
 * 
 * @author DougLei
 */
public class SwitchSqlNode extends AbstractNestingNode {
	private static final long serialVersionUID = -4287261067377795975L;

	@Override
	public ExecuteSqlNode getExecuteSqlNode(Object sqlParameter, String sqlParameterNamePrefix) {
		for (SqlNode sqlNode : sqlNodes) {
			if(sqlNode.matching(sqlParameter, sqlParameterNamePrefix)) {
				return sqlNode.getExecuteSqlNode(sqlParameter, sqlParameterNamePrefix);
			}
		}
		return ExecuteSqlNode.emptyExecuteSqlNode();
	}
	
	@Override
	public ValidationResult validateParameter(Object sqlParameter, String sqlParameterNamePrefix) {
		for (SqlNode sqlNode : sqlNodes) {
			if(sqlNode.matching(sqlParameter, sqlParameterNamePrefix)) {
				return sqlNode.validateParameter(sqlParameter, sqlParameterNamePrefix);
			}
		}
		return null;
	}
	
	@Override
	public SqlNodeType getType() {
		return SqlNodeType.SWITCH;
	}
}