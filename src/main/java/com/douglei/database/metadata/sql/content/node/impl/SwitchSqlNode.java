package com.douglei.database.metadata.sql.content.node.impl;

import com.douglei.database.metadata.sql.content.node.ExecuteSqlNode;
import com.douglei.database.metadata.sql.content.node.SqlNode;
import com.douglei.database.metadata.sql.content.node.SqlNodeType;

/**
 * 
 * @author DougLei
 */
public class SwitchSqlNode extends AbstractNestingNode {

	@Override
	public boolean matching(Object sqlParameter) {
		return true;
	}
	
	@Override
	public ExecuteSqlNode getExecuteSqlNode(Object sqlParameter) {
		for (SqlNode sqlNode : sqlNodes) {
			if(sqlNode.matching(sqlParameter)) {
				return sqlNode.getExecuteSqlNode(sqlParameter);
			}
		}
		return null;
	}
	
	@Override
	public SqlNodeType getType() {
		return SqlNodeType.SWITCH;
	}
}