package com.douglei.orm.mapping.impl.sql.metadata.content.node.impl;

import com.douglei.orm.mapping.impl.sql.metadata.content.node.ExecuteSqlNode;
import com.douglei.orm.mapping.impl.sql.metadata.content.node.SqlNode;
import com.douglei.orm.mapping.impl.sql.metadata.content.node.SqlNodeType;
import com.douglei.orm.mapping.metadata.validator.ValidationResult;
import com.douglei.orm.sessionfactory.sessions.session.sql.PurposeEntity;

/**
 * 
 * @author DougLei
 */
public class SwitchSqlNode extends AbstractNestingNode {
	private static final long serialVersionUID = 6112742403201533418L;

	@Override
	public ExecuteSqlNode getExecuteSqlNode(PurposeEntity purposeEntity, Object sqlParameter, String alias) {
		for (SqlNode sqlNode : sqlNodes) {
			if(sqlNode.matching(sqlParameter, alias)) 
				return sqlNode.getExecuteSqlNode(purposeEntity, sqlParameter, alias);
		}
		return ExecuteSqlNode.emptyExecuteSqlNode();
	}
	
	@Override
	public ValidationResult validateParameter(Object sqlParameter, String alias) {
		for (SqlNode sqlNode : sqlNodes) {
			if(sqlNode.matching(sqlParameter, alias)) {
				return sqlNode.validateParameter(sqlParameter, alias);
			}
		}
		return null;
	}
	
	@Override
	public SqlNodeType getType() {
		return SqlNodeType.SWITCH;
	}
}