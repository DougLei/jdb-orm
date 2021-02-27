package com.douglei.orm.mapping.impl.sql.parser.content.node.impl;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import com.douglei.orm.mapping.impl.sql.metadata.content.node.SqlNode;
import com.douglei.orm.mapping.impl.sql.metadata.content.node.impl.ForeachSqlNode;
import com.douglei.orm.mapping.impl.sql.parser.content.node.SqlNodeParser;
import com.douglei.orm.mapping.impl.sql.parser.content.node.SqlNodeParserException;
import com.douglei.orm.mapping.impl.sql.parser.content.node.SqlNodeType;
import com.douglei.tools.StringUtil;

/**
 * 
 * @author DougLei
 */
public class ForeachSqlNodeParser extends SqlNodeParser {

	@Override
	public SqlNode parse(Node node) throws SqlNodeParserException {
		NamedNodeMap attributeMap = node.getAttributes();
		
		String collection = getAttributeValue(attributeMap.getNamedItem("collection"));
		if(StringUtil.isEmpty(collection)) {
			throw new SqlNodeParserException("<foreach>中的collection属性值不能为空");
		}
		
		String alias = getAttributeValue(attributeMap.getNamedItem("alias"));
		if(StringUtil.isEmpty(alias)) 
			throw new SqlNodeParserException("<foreach>中的alias属性值不能为空");
		
		// 解析open
		String open = getAttributeValue(attributeMap.getNamedItem("open"));
		open = open==null?" ":" "+open+" ";
		
		// 解析separator
		String separator = getAttributeValue(attributeMap.getNamedItem("separator"));
		separator = separator==null?" ":separator;
		
		// 解析close
		String close = getAttributeValue(attributeMap.getNamedItem("close"));
		close = close==null?" ":" "+close+" ";
		
		ForeachSqlNode foreachSqlNode = new ForeachSqlNode(collection.trim(), alias.trim(), open, separator, close);
		setChildrenSqlNodes(foreachSqlNode, node);
		return foreachSqlNode;
	}
	
	@Override
	public SqlNodeType getNodeType() {
		return SqlNodeType.FOREACH;
	}
}
