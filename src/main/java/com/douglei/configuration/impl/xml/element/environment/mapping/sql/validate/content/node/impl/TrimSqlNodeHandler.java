package com.douglei.configuration.impl.xml.element.environment.mapping.sql.validate.content.node.impl;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import com.douglei.configuration.impl.xml.element.environment.mapping.sql.validate.content.node.SqlNodeHandler;
import com.douglei.database.metadata.sql.content.node.SqlNode;
import com.douglei.database.metadata.sql.content.node.impl.TrimSqlNode;
import com.douglei.utils.StringUtil;

/**
 * 
 * @author DougLei
 */
public class TrimSqlNodeHandler implements SqlNodeHandler {

	@Override
	public SqlNode doHandler(Node node) {
		String allContent = node.getTextContent();
		if(StringUtil.isEmpty(allContent)) {
			return null;
		}
		NamedNodeMap attributeMap = node.getAttributes();
		TrimSqlNode trimSqlNode = new TrimSqlNode(
				attributeMap.getNamedItem("prefix").getNodeValue(),
				attributeMap.getNamedItem("prefixoverride").getNodeValue(),
				attributeMap.getNamedItem("suffix").getNodeValue(),
				attributeMap.getNamedItem("suffixoverride").getNodeValue());
		
		
		
		return trimSqlNode;
	}
	
	@Override
	public String getNodeName() {
		return "trim";
	}
}
