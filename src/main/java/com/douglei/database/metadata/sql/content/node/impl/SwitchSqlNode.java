package com.douglei.database.metadata.sql.content.node.impl;

import java.util.Map;

import com.douglei.database.metadata.sql.content.node.ExecuteSqlNode;
import com.douglei.database.metadata.sql.content.node.SqlNode;
import com.douglei.database.metadata.sql.content.node.SqlNodeType;

/**
 * 
 * @author DougLei
 */
public class SwitchSqlNode extends AbstractNestingNode {

	@Override
	public boolean matching(Map<String, Object> sqlParameterMap) {
		return true;
	}
	
	@Override
	public ExecuteSqlNode getExecuteSqlNode(Map<String, Object> sqlParameterMap) {
		for (SqlNode sqlNode : sqlNodes) {
			if(sqlNode.matching(sqlParameterMap)) {
				return sqlNode.getExecuteSqlNode(sqlParameterMap);
			}
		}
		return null;
	}
	
	@Override
	public SqlNodeType getType() {
		return SqlNodeType.SWITCH;
	}
}