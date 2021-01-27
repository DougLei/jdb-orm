package com.douglei.orm.mapping.impl.sql.metadata.content.node.impl;

import com.douglei.orm.mapping.impl.sql.metadata.content.node.ExecutableSqlNode;
import com.douglei.orm.mapping.impl.sql.metadata.content.node.SqlNode;
import com.douglei.orm.mapping.impl.sql.metadata.parser.content.node.SqlNodeType;
import com.douglei.orm.mapping.metadata.validator.ValidationResult;
import com.douglei.orm.sessionfactory.sessions.session.sql.PurposeEntity;

/**
 * 
 * @author DougLei
 */
public class SwitchSqlNode extends AbstractNestingNode {
	private static final long serialVersionUID = -6227222732922449642L;

	@Override
	public SqlNodeType getType() {
		return SqlNodeType.SWITCH;
	}
	
	@Override
	public ExecutableSqlNode getExecutableSqlNode(PurposeEntity purposeEntity, Object sqlParameter, String previousAlias) {
		for (SqlNode sqlNode : sqlNodes) {
			if(sqlNode.matching(sqlParameter, previousAlias)) 
				return sqlNode.getExecutableSqlNode(purposeEntity, sqlParameter, previousAlias);
		}
		return ExecutableSqlNode.emptyExecutableSqlNode();
	}
	
	@Override
	public ValidationResult validateParameter(Object sqlParameter, String previousAlias) {
		for (SqlNode sqlNode : sqlNodes) {
			if(sqlNode.matching(sqlParameter, previousAlias)) {
				return sqlNode.validateParameter(sqlParameter, previousAlias);
			}
		}
		return null;
	}
}