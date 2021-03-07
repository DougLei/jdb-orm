package com.douglei.orm.mapping.impl.sql.parser.content.node.impl;

import org.w3c.dom.Node;

import com.douglei.orm.mapping.impl.sql.SqlNodeType;
import com.douglei.orm.mapping.impl.sql.metadata.content.node.SqlNode;
import com.douglei.orm.mapping.impl.sql.metadata.content.node.impl.SwitchSqlNode;
import com.douglei.orm.mapping.impl.sql.parser.content.node.SqlNodeParser;
import com.douglei.orm.mapping.impl.sql.parser.content.node.SqlNodeParseException;

/**
 * 
 * @author DougLei
 */
public class SwitchSqlNodeParser extends SqlNodeParser {

	@Override
	public SqlNode parse(Node node) throws SqlNodeParseException {
		SwitchSqlNode switchSqlNode = new SwitchSqlNode();
		setChildrenSqlNodes(switchSqlNode, node);
		return switchSqlNode;
	}
	
	@Override
	protected void validateChildrenSqlNode(SqlNode children) throws SqlNodeParseException {
		if(children.getType() != SqlNodeType.IF && children.getType() != SqlNodeType.ELSE) 
			throw new SqlNodeParseException("<switch>中, 只能使用<if>或<else>; 也不支持在<switch>中, 直接编写sql语句");
	}

	@Override
	public SqlNodeType getType() {
		return SqlNodeType.SWITCH;
	}
}
