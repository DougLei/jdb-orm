package com.douglei.orm.mapping.impl.sql.parser.content.node.impl;

import org.w3c.dom.Node;

import com.douglei.orm.mapping.impl.sql.metadata.content.node.SqlNode;
import com.douglei.orm.mapping.impl.sql.metadata.content.node.impl.SwitchSqlNode;
import com.douglei.orm.mapping.impl.sql.parser.content.node.SqlNodeParser;
import com.douglei.orm.mapping.impl.sql.parser.content.node.SqlNodeParserException;
import com.douglei.orm.mapping.impl.sql.parser.content.node.SqlNodeType;

/**
 * 
 * @author DougLei
 */
public class SwitchSqlNodeParser extends SqlNodeParser {

	@Override
	public SqlNode parse(Node node) throws SqlNodeParserException {
		SwitchSqlNode switchSqlNode = new SwitchSqlNode();
		setChildrenSqlNodes(switchSqlNode, node);
		return switchSqlNode;
	}
	
	@Override
	protected void validateChildrenSqlNode(SqlNode children) throws SqlNodeParserException {
		if(children.getType() != SqlNodeType.IF && children.getType() != SqlNodeType.ELSE) 
			throw new SqlNodeParserException("<switch>中, 只能使用<if>或<else>; 也不支持在<switch>中, 直接编写sql语句");
	}

	@Override
	public SqlNodeType getNodeType() {
		return SqlNodeType.SWITCH;
	}
}
