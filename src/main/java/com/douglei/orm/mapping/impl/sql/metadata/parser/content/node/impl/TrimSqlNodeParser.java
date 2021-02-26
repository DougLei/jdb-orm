package com.douglei.orm.mapping.impl.sql.metadata.parser.content.node.impl;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import com.douglei.orm.mapping.impl.sql.metadata.content.node.SqlNode;
import com.douglei.orm.mapping.impl.sql.metadata.content.node.impl.TrimSqlNode;
import com.douglei.orm.mapping.impl.sql.metadata.parser.content.node.SqlNodeParser;
import com.douglei.orm.mapping.impl.sql.metadata.parser.content.node.SqlNodeParserException;
import com.douglei.orm.mapping.impl.sql.metadata.parser.content.node.SqlNodeType;

/**
 * 
 * @author DougLei
 */
public class TrimSqlNodeParser extends SqlNodeParser {

	@Override
	public SqlNode parse(Node node) throws SqlNodeParserException {
		NamedNodeMap attributeMap = node.getAttributes();
		TrimSqlNode trimSqlNode = new TrimSqlNode(
				getAttributeValue(attributeMap.getNamedItem("prefix")), 
				getAttributeValue(attributeMap.getNamedItem("suffix")), 
				getAttributeValue(attributeMap.getNamedItem("prefixoverride")), 
				getAttributeValue(attributeMap.getNamedItem("suffixoverride")));
		setChildrenSqlNodes(trimSqlNode, node);
		return trimSqlNode;
	}
	
	@Override
	protected void validateChildrenSqlNode(SqlNode children) throws SqlNodeParserException {
		if(children.getType() != SqlNodeType.IF)
			throw new SqlNodeParserException("<trim>中, 只能使用<if>; 也不支持在<trim>中, 直接编写sql语句");
	}

	@Override
	public SqlNodeType getNodeType() {
		return SqlNodeType.TRIM;
	}
}
