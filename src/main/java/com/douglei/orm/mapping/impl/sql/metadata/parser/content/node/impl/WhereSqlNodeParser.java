package com.douglei.orm.mapping.impl.sql.metadata.parser.content.node.impl;

import org.w3c.dom.Node;

import com.douglei.orm.mapping.impl.sql.metadata.content.node.SqlNode;
import com.douglei.orm.mapping.impl.sql.metadata.content.node.impl.WhereSqlNode;
import com.douglei.orm.mapping.impl.sql.metadata.parser.content.node.SqlNodeParser;
import com.douglei.orm.mapping.impl.sql.metadata.parser.content.node.SqlNodeParserException;
import com.douglei.orm.mapping.impl.sql.metadata.parser.content.node.SqlNodeType;

/**
 * 
 * @author DougLei
 */
public class WhereSqlNodeParser extends SqlNodeParser {

	@Override
	public SqlNode parse(Node node) throws SqlNodeParserException {
		WhereSqlNode whereSqlNode = new WhereSqlNode();
		setChildrenSqlNodes(whereSqlNode, node);
		return whereSqlNode;
	}
	
	@Override
	protected void validateChildrenSqlNode(SqlNode children) throws SqlNodeParserException {
		if(children.getType() != SqlNodeType.IF) 
			throw new SqlNodeParserException("<where>中, 只能使用<if>; 也不支持在<where>中, 直接编写sql语句");
	}

	@Override
	public SqlNodeType getNodeType() {
		return SqlNodeType.WHERE;
	}
}
