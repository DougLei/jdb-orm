package com.douglei.orm.configuration.impl.element.environment.mapping.sql.resolver.content.node.impl;

import org.w3c.dom.Node;

import com.douglei.orm.configuration.impl.element.environment.mapping.sql.resolver.content.node.SqlNodeResolver;
import com.douglei.orm.configuration.impl.element.environment.mapping.sql.resolver.content.node.SqlNodeResolvingException;
import com.douglei.orm.core.metadata.sql.content.node.SqlNode;
import com.douglei.orm.core.metadata.sql.content.node.impl.ElseSqlNode;
import com.douglei.tools.utils.StringUtil;

/**
 * 
 * @author DougLei
 */
public class ElseSqlNodeResolver implements SqlNodeResolver {

	@Override
	public SqlNode resolving(Node node) {
		String content = node.getTextContent();
		if(StringUtil.isEmpty(content)) {
			throw new SqlNodeResolvingException("<else>元素中的内容不能为空");
		}
		return new ElseSqlNode(content);
	}
	
	@Override
	public String getNodeName() {
		return "else";
	}
}
