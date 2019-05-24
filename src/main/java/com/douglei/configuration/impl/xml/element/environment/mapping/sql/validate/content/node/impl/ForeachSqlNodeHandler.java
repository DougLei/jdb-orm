package com.douglei.configuration.impl.xml.element.environment.mapping.sql.validate.content.node.impl;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import com.douglei.configuration.impl.xml.element.environment.mapping.sql.validate.content.node.SqlNodeHandler;
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
		String content = node.getTextContent();
		if(StringUtil.isEmpty(content)) {
			throw new NullPointerException("<foreach>元素中不存在任何sql语句");
		}
		
		NamedNodeMap attributeMap = node.getAttributes();
		Node collectionAttributeNode = attributeMap.getNamedItem("collection");
		if(collectionAttributeNode == null) {
			throw new NullPointerException("<foreach>元素中的collection属性值不能为空");
		}
		
		String alias = null;
		Node aliasAttributeNode = attributeMap.getNamedItem("alias");
		if(aliasAttributeNode != null) {
			String tmpAlias = aliasAttributeNode.getNodeValue();
			if(StringUtil.notEmpty(tmpAlias)) {
				alias = tmpAlias.trim();
			}
		}
		
		return new ForeachSqlNode(
				content,
				collectionAttributeNode.getNodeValue(),
				alias,
				attributeMap.getNamedItem("open"),
				attributeMap.getNamedItem("separator"),
				attributeMap.getNamedItem("close"));
	}
	
	@Override
	public String getNodeName() {
		return "foreach";
	}
}
