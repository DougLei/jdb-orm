package com.douglei.orm.configuration.impl.element.environment.mapping.sql.resolver.content.node.impl;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.douglei.orm.configuration.impl.element.environment.mapping.sql.resolver.content.node.SqlNodeResolver;
import com.douglei.orm.configuration.impl.element.environment.mapping.sql.resolver.content.node.SqlNodeResolverMapping;
import com.douglei.orm.configuration.impl.element.environment.mapping.sql.resolver.content.node.SqlNodeResolvingException;
import com.douglei.orm.core.metadata.sql.content.node.SqlNode;
import com.douglei.orm.core.metadata.sql.content.node.impl.ForeachSqlNode;
import com.douglei.tools.utils.StringUtil;

/**
 * 
 * @author DougLei
 */
public class ForeachSqlNodeResolver implements SqlNodeResolver {

	@Override
	public SqlNode resolving(Node node) {
		NodeList childrens = node.getChildNodes();
		int cl = childrens.getLength();
		if(cl == 0) {
			throw new SqlNodeResolvingException("<foreach>元素中不存在任何sql语句");
		}
		
		NamedNodeMap attributeMap = node.getAttributes();
		
		String collection = getAttributeValue(attributeMap.getNamedItem("collection"));
		if(StringUtil.isEmpty(collection)) {
			throw new SqlNodeResolvingException("<foreach>元素中的collection属性值不能为空");
		}
		
		String alias = getAttributeValue(attributeMap.getNamedItem("alias"));
		if(StringUtil.isEmpty(alias)) {
			throw new SqlNodeResolvingException("<foreach>元素中的alias属性值不能为空");
		}
		
		ForeachSqlNode foreachSqlNode = new ForeachSqlNode(collection.trim(), alias.trim(),
				getAttributeValue(attributeMap.getNamedItem("open")),
				getAttributeValue(attributeMap.getNamedItem("separator")),
				getAttributeValue(attributeMap.getNamedItem("close")));
		
		SqlNode sqlNode = null;
		for(int i=0;i<cl;i++) {
			sqlNode = SqlNodeResolverMapping.resolving(childrens.item(i));
			if(sqlNode != null) {
				foreachSqlNode.addSqlNode(sqlNode);
			}
		}
		if(foreachSqlNode.existsSqlNode()) {
			return foreachSqlNode;
		}
		throw new SqlNodeResolvingException("<foreach>元素中不存在任何sql语句");
	}
	
	@Override
	public String getNodeName() {
		return "foreach";
	}
}
