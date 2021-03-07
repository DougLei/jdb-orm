package com.douglei.orm.mapping.impl.sql.parser.content.node.impl;

import org.w3c.dom.Node;

import com.douglei.orm.mapping.impl.sql.SqlNodeType;
import com.douglei.orm.mapping.impl.sql.metadata.content.node.SqlNode;
import com.douglei.orm.mapping.impl.sql.metadata.content.node.impl.ElseSqlNode;
import com.douglei.orm.mapping.impl.sql.parser.content.node.SqlNodeParser;
import com.douglei.orm.mapping.impl.sql.parser.content.node.SqlNodeParseException;

/**
 * 
 * @author DougLei
 */
public class ElseSqlNodeParser extends SqlNodeParser {

	@Override
	public SqlNode parse(Node node) throws SqlNodeParseException {
		ElseSqlNode elseSqlNode = new ElseSqlNode();
		setChildrenSqlNodes(elseSqlNode, node);
		return elseSqlNode;
	}
	
	@Override
	public SqlNodeType getType() {
		return SqlNodeType.ELSE;
	}
}
