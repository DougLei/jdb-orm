package com.douglei.database.metadata.sql.content.node.impl;

import java.util.Map;

import com.douglei.database.metadata.sql.content.node.ExecuteSqlNode;
import com.douglei.database.metadata.sql.content.node.SqlNodeType;

/**
 * 
 * @author DougLei
 */
public class ForeachSqlNode extends AbstractNestingNode {
	
	public ForeachSqlNode() {
	}
	
	@Override
	public boolean matching(Map<String, Object> sqlParameterMap) {
		return true;
	}
	
	@Override
	public ExecuteSqlNode getExecuteSqlNode(Map<String, Object> sqlParameterMap) {
		return null;
	}
	
	@Override
	public SqlNodeType getType() {
		return SqlNodeType.FOREACH;
	}
}