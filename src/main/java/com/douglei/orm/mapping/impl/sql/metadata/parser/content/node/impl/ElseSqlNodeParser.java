package com.douglei.orm.mapping.impl.sql.metadata.parser.content.node.impl;

import org.w3c.dom.Node;

import com.douglei.orm.mapping.impl.sql.metadata.content.node.SqlNode;
import com.douglei.orm.mapping.impl.sql.metadata.content.node.impl.ElseSqlNode;
import com.douglei.orm.mapping.impl.sql.metadata.parser.content.node.SqlNodeParser;
import com.douglei.orm.mapping.impl.sql.metadata.parser.content.node.SqlNodeParserException;
import com.douglei.tools.utils.StringUtil;

/**
 * 
 * @author DougLei
 */
public class ElseSqlNodeParser implements SqlNodeParser {

	@Override
	public SqlNode parse(Node node) throws SqlNodeParserException {
		String content = node.getTextContent();
		if(StringUtil.isEmpty(content)) {
			throw new SqlNodeParserException("<else>元素中的内容不能为空");
		}
		return new ElseSqlNode(content);
	}
	
	@Override
	public String getNodeName() {
		return "else";
	}
}
