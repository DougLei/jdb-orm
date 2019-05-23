package com.douglei.configuration.impl.xml.element.environment.mapping.sql.validate.content.node.impl;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.douglei.configuration.impl.xml.element.environment.mapping.sql.validate.content.node.SqlNodeHandler;
import com.douglei.configuration.impl.xml.element.environment.mapping.sql.validate.content.node.SqlNodeHandlerMapping;
import com.douglei.database.metadata.sql.content.node.SqlNode;
import com.douglei.database.metadata.sql.content.node.impl.SetSqlNode;

/**
 * 
 * @author DougLei
 */
public class SetSqlNodeHandler implements SqlNodeHandler {

	@Override
	public SqlNode doHandler(Node node) {
		NodeList childrens = node.getChildNodes();
		int cl = childrens.getLength();
		if(cl == 0) {
			throw new NullPointerException("<set>元素中不存在任何元素配置");
		}
		
		SetSqlNode setSqlNode = new SetSqlNode();
		for(int i=0;i<cl;i++) {
			setSqlNode.addSqlNode(SqlNodeHandlerMapping.doHandler(childrens.item(i)));
		}
		if(setSqlNode.existsSqlNode()) {
			return setSqlNode;
		}
		throw new NullPointerException("<set>元素中不存在任何元素配置");
	}
	
	@Override
	public String getNodeName() {
		return "set";
	}
}
