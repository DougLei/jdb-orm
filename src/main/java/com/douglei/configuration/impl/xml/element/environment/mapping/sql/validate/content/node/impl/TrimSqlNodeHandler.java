package com.douglei.configuration.impl.xml.element.environment.mapping.sql.validate.content.node.impl;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.douglei.configuration.impl.xml.element.environment.mapping.sql.validate.content.node.SqlNodeHandler;
import com.douglei.configuration.impl.xml.element.environment.mapping.sql.validate.content.node.SqlNodeHandlerMapping;
import com.douglei.database.metadata.sql.content.node.SqlNode;
import com.douglei.database.metadata.sql.content.node.impl.TrimSqlNode;

/**
 * 
 * @author DougLei
 */
public class TrimSqlNodeHandler implements SqlNodeHandler {

	@Override
	public SqlNode doHandler(Node node) {
		NodeList childrens = node.getChildNodes();
		int cl = childrens.getLength();
		if(cl == 0) {
			throw new NullPointerException("<trim>元素中不存在任何sql语句");
		}
		
		NamedNodeMap attributeMap = node.getAttributes();
		TrimSqlNode trimSqlNode = new TrimSqlNode(
				attributeMap.getNamedItem("prefix"),
				attributeMap.getNamedItem("suffix"),
				attributeMap.getNamedItem("prefixoverride"),
				attributeMap.getNamedItem("suffixoverride"));
		
		for(int i=0;i<cl;i++) {
			trimSqlNode.addSqlNode(SqlNodeHandlerMapping.doHandler(childrens.item(i)));
		}
		if(trimSqlNode.existsSqlNode()) {
			return trimSqlNode;
		}
		throw new NullPointerException("<trim>元素中不存在任何sql语句");
	}
	
	@Override
	public String getNodeName() {
		return "trim";
	}
}
