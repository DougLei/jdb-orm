package com.douglei.orm.mapping.impl.sql.metadata.content.node.impl;

import com.douglei.orm.mapping.impl.sql.SqlNodeType;
import com.douglei.orm.mapping.impl.sql.metadata.content.node.SqlNode;

/**
 * 
 * @author DougLei
 */
public class IncludeSqlNode implements SqlNode {
	
	private String refName; 
	public IncludeSqlNode(String refName) {
		this.refName = refName;
	}

	public String getRefName() {
		return refName;
	}

	@Override
	public SqlNodeType getType() {
		return SqlNodeType.INCLUDE;
	}
}
