package com.douglei.configuration.impl.xml.element.environment.mapping.sql.validate.content.node.impl;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.douglei.configuration.impl.xml.element.environment.mapping.sql.validate.content.node.SqlNodeHandler;
import com.douglei.configuration.impl.xml.element.environment.mapping.sql.validate.content.node.SqlNodeHandlerMapping;
import com.douglei.database.metadata.sql.content.node.SqlNode;
import com.douglei.database.metadata.sql.content.node.impl.ForeachSqlNode;
import com.douglei.utils.StringUtil;

/**
 * 
 * @author DougLei
 */
public class ForeachSqlNodeHandler implements SqlNodeHandler {

	@Override
	public SqlNode doHandler(Node node) {
		NodeList childrens = node.getChildNodes();
		int cl = childrens.getLength();
		if(cl == 0) {
			throw new NullPointerException("<foreach>元素中不存在任何sql语句");
		}
		
		NamedNodeMap attributeMap = node.getAttributes();
		
		Node collectionAttributeNode = attributeMap.getNamedItem("collection");
		String collection = null;
		if(collectionAttributeNode == null || StringUtil.isEmpty((collection = collectionAttributeNode.getNodeValue()))) {
			throw new NullPointerException("<foreach>元素中的collection属性值不能为空");
		}
		
		Node aliasAttributeNode = attributeMap.getNamedItem("alias");
		String alias = null;
		if(aliasAttributeNode == null || StringUtil.isEmpty((alias = aliasAttributeNode.getNodeValue()))) {
			throw new NullPointerException("<foreach>元素中的alias属性值不能为空");
		}
		
		ForeachSqlNode foreachSqlNode = new ForeachSqlNode(
				collection.trim(), alias.trim(),
				attributeMap.getNamedItem("open"),
				attributeMap.getNamedItem("separator"),
				attributeMap.getNamedItem("close"));
		
		for(int i=0;i<cl;i++) {
			foreachSqlNode.addSqlNode(SqlNodeHandlerMapping.doHandler(childrens.item(i)));
		}
		if(foreachSqlNode.existsSqlNode()) {
			return foreachSqlNode;
		}
		throw new NullPointerException("<foreach>元素中不存在任何sql语句");
	}
	
	@Override
	public String getNodeName() {
		return "foreach";
	}
}
