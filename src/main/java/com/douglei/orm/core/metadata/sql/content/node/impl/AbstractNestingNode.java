package com.douglei.orm.core.metadata.sql.content.node.impl;

import java.util.ArrayList;
import java.util.List;

import com.douglei.orm.core.metadata.sql.content.node.SqlNode;

/**
 * 嵌套node
 * @author DougLei
 */
public abstract class AbstractNestingNode implements SqlNode{
	private static final long serialVersionUID = 1196837802103130661L;
	protected List<SqlNode> sqlNodes;
	public void addSqlNode(SqlNode sqlNode) {
		if(sqlNodes == null) {
			sqlNodes = new ArrayList<SqlNode>(10);
		}
		sqlNodes.add(sqlNode);
	}
	
	public boolean existsSqlNode() {
		return sqlNodes != null;
	}
}
