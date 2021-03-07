package com.douglei.orm.mapping.impl.sql.executor.content.node.impl;

import com.douglei.orm.mapping.impl.sql.SqlNodeType;
import com.douglei.orm.mapping.impl.sql.executor.content.node.SqlNodeExecutor;
import com.douglei.orm.mapping.impl.sql.metadata.content.node.impl.ElseSqlNode;

/**
 * 
 * @author DougLei
 */
public class ElseSqlNodeExecutor extends AbstractNestingNodeExecutor<ElseSqlNode> implements SqlNodeExecutor<ElseSqlNode> {

	@Override
	public SqlNodeType getType() {
		return SqlNodeType.ELSE;
	}
}
