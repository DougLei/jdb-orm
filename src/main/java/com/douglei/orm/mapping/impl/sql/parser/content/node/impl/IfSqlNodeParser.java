package com.douglei.orm.mapping.impl.sql.parser.content.node.impl;

import org.w3c.dom.Node;

import com.douglei.orm.mapping.impl.sql.metadata.content.node.SqlNode;
import com.douglei.orm.mapping.impl.sql.metadata.content.node.SqlNodeType;
import com.douglei.orm.mapping.impl.sql.metadata.content.node.impl.IfSqlNode;
import com.douglei.orm.mapping.impl.sql.parser.content.node.SqlNodeParser;
import com.douglei.orm.mapping.impl.sql.parser.content.node.SqlNodeParserException;
import com.douglei.tools.StringUtil;

/**
 * 
 * @author DougLei
 */
public class IfSqlNodeParser extends SqlNodeParser {

	@Override
	public SqlNode parse(Node node) throws SqlNodeParserException {
		String expression = getAttributeValue(node.getAttributes().getNamedItem("test"));
		if(StringUtil.isEmpty(expression)) 
			throw new SqlNodeParserException("<if>中的test属性值不能为空");
		
		IfSqlNode ifSqlNode = new IfSqlNode(expression);
		setChildrenSqlNodes(ifSqlNode, node);
		return ifSqlNode;
	}
	
	@Override
	public SqlNodeType getNodeType() {
		return SqlNodeType.IF;
	}
}
