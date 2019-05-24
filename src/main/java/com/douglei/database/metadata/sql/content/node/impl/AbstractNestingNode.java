package com.douglei.database.metadata.sql.content.node.impl;

import java.util.ArrayList;
import java.util.List;

import com.douglei.database.metadata.sql.content.node.SqlNode;

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
}
