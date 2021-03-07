package com.douglei.orm.mapping.impl.sql.parser.content.node.impl;

import org.w3c.dom.Node;

import com.douglei.orm.mapping.impl.sql.SqlNodeType;
import com.douglei.orm.mapping.impl.sql.metadata.content.node.SqlNode;
import com.douglei.orm.mapping.impl.sql.metadata.content.node.impl.WhereSqlNode;
import com.douglei.orm.mapping.impl.sql.parser.content.node.SqlNodeParser;
import com.douglei.orm.mapping.impl.sql.parser.content.node.SqlNodeParseException;

/**
 * 
 * @author DougLei
 */
public class WhereSqlNodeParser extends SqlNodeParser {

	@Override
	public SqlNode parse(Node node) throws SqlNodeParseException {
		WhereSqlNode whereSqlNode = new WhereSqlNode();
		setChildrenSqlNodes(whereSqlNode, node);
		return whereSqlNode;
	}
	
	@Override
	protected void validateChildrenSqlNode(SqlNode children) throws SqlNodeParseException {
		if(children.getType() != SqlNodeType.IF) 
			throw new SqlNodeParseException("<where>中, 只能使用<if>; 也不支持在<where>中, 直接编写sql语句");
	}

	@Override
	public SqlNodeType getType() {
		return SqlNodeType.WHERE;
	}
}
