package com.douglei.orm.configuration.impl.element.environment.mapping.sql.resolver.content.node.impl;

import org.w3c.dom.Node;

import com.douglei.orm.configuration.impl.element.environment.mapping.sql.resolver.content.node.SqlNodeResolver;
import com.douglei.orm.core.metadata.sql.content.node.SqlNode;
import com.douglei.orm.core.metadata.sql.content.node.impl.TextSqlNode;
import com.douglei.tools.utils.StringUtil;

/**
 * 
 * @author DougLei
 */
public class TextSqlNodeResolver implements SqlNodeResolver {

	@Override
	public SqlNode resolving(Node node) {
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
