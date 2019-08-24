package com.douglei.orm.core.metadata.sql.content.node.impl;

import com.douglei.orm.core.metadata.sql.SqlContentMetadata;
import com.douglei.orm.core.metadata.sql.content.node.ExecuteSqlNode;
import com.douglei.orm.core.metadata.sql.content.node.SqlNode;
import com.douglei.orm.core.metadata.sql.content.node.SqlNodeType;

/**
 * 
 * @author DougLei
 */
public class IncludeSqlNode implements SqlNode {
	private SqlContentMetadata sqlContent;
	
	public IncludeSqlNode(SqlContentMetadata sqlContent) {
		this.sqlContent = sqlContent;
	}

	@Override
	public SqlNodeType getType() {
		return SqlNodeType.INCLUDE;
	}

	@Override
	public ExecuteSqlNode getExecuteSqlNode(Object sqlParameter, String sqlParameterNamePrefix) {
		// TODO Auto-generated method stub
		return null;
	}
}
