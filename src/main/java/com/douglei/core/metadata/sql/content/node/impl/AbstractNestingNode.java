package com.douglei.core.metadata.sql.content.node.impl;

import java.util.ArrayList;
import java.util.List;

import com.douglei.core.metadata.sql.content.node.ExecuteSqlNode;
import com.douglei.core.metadata.sql.content.node.SqlNode;

/**
 * 嵌套node
 * @author DougLei
 */
public abstract class AbstractNestingNode implements SqlNode{
	
	protected List<SqlNode> sqlNodes;
	public void addSqlNode(SqlNode sqlNode) {
		if(sqlNode == null) {
			return;
		}
		if(sqlNodes == null) {
			sqlNodes = new ArrayList<SqlNode>();
		}
		sqlNodes.add(sqlNode);
	}
	
	public boolean existsSqlNode() {
		return sqlNodes != null;
	}
	
	@Override
	public boolean matching(Object sqlParameter) {
		return matching(sqlParameter, null);
	}
	
	@Override
	public boolean matching(Object sqlParameter, String alias) {
		return true;
	}
	
	@Override
	public ExecuteSqlNode getExecuteSqlNode(Object sqlParameter) {
		return getExecuteSqlNode(sqlParameter, null);
	}
}
