package com.douglei.orm.mapping.impl.sql.metadata.content.node.impl;

import com.douglei.orm.mapping.impl.sql.metadata.parser.content.node.SqlNodeType;

/**
 * 
 * @author DougLei
 */
public class ElseSqlNode extends AbstractNestingNode {
	private static final long serialVersionUID = -8580796613861994797L;

	@Override
	public SqlNodeType getType() {
		return SqlNodeType.ELSE;
	}
}
