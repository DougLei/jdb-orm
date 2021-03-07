package com.douglei.orm.mapping.impl.sql.metadata.content.node.impl;

import com.douglei.orm.mapping.impl.sql.SqlNodeType;

/**
 * 
 * @author DougLei
 */
public class ElseSqlNode extends AbstractNestingNode {

	@Override
	public SqlNodeType getType() {
		return SqlNodeType.ELSE;
	}
}
