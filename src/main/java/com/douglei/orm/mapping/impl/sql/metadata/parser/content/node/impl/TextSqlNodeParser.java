package com.douglei.orm.mapping.impl.sql.metadata.parser.content.node.impl;

import org.w3c.dom.Node;

import com.douglei.orm.mapping.impl.sql.metadata.content.node.SqlNode;
import com.douglei.orm.mapping.impl.sql.metadata.content.node.impl.TextSqlNode;
import com.douglei.orm.mapping.impl.sql.metadata.parser.content.node.SqlNodeParser;
import com.douglei.orm.mapping.impl.sql.metadata.parser.content.node.SqlNodeParserException;
import com.douglei.tools.utils.StringUtil;

/**
 * 
 * @author DougLei
 */
public class TextSqlNodeParser implements SqlNodeParser {

	@Override
	public SqlNode parse(Node node) throws SqlNodeParserException {
		String content = node.getNodeValue();
		if(StringUtil.isEmpty(content)) {
			return null;
		}
		return new TextSqlNode(content);
	}

	@Override
	public String getNodeName() {
		return "#text";
	}
}
