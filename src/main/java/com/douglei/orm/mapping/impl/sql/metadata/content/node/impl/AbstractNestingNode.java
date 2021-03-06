package com.douglei.orm.mapping.impl.sql.metadata.content.node.impl;

import java.util.ArrayList;
import java.util.List;

import com.douglei.orm.mapping.impl.sql.metadata.content.node.SqlNode;

/**
 * 嵌套node
 * @author DougLei
 */
public abstract class AbstractNestingNode implements SqlNode{
	protected List<SqlNode> sqlNodes;// 内部的node集合
	
	/**
	 * 添加内部node
	 * @param sqlNode
	 */
	public void addSqlNode(SqlNode sqlNode) {
		if(sqlNodes == null) 
			sqlNodes = new ArrayList<SqlNode>();
		sqlNodes.add(sqlNode);
	}
	
	/**
	 * 是否存在内部node
	 * @return
	 */
	public boolean existsSqlNode() {
		return sqlNodes != null;
	}

	/**
	 * 
	 * @return
	 */
	public List<SqlNode> getSqlNodes() {
		return sqlNodes;
	}
}
