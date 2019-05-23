package com.douglei.configuration.impl.xml.element.environment.mapping.sql.validate.content.node.impl;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.douglei.configuration.impl.xml.element.environment.mapping.sql.validate.content.node.SqlNodeHandler;
import com.douglei.configuration.impl.xml.element.environment.mapping.sql.validate.content.node.SqlNodeHandlerMapping;
import com.douglei.database.metadata.sql.content.node.SqlNode;
import com.douglei.database.metadata.sql.content.node.impl.WhereSqlNode;

/**
 * 
 * @author DougLei
 */
public class WhereSqlNodeHandler implements SqlNodeHandler {

	@Override
	public SqlNode doHandler(Node node) {
		NodeList childrens = node.getChildNodes();
		int cl = childrens.getLength();
		if(cl == 0) {
			throw new NullPointerException("<where>元素中不存在任何元素配置");
		}
		
		WhereSqlNode whereSqlNode = new WhereSqlNode();
		for(int i=0;i<cl;i++) {
			whereSqlNode.addSqlNode(SqlNodeHandlerMapping.doHandler(childrens.item(i)));
		}
		if(whereSqlNode.existsSqlNode()) {
			return whereSqlNode;
		}
		throw new NullPointerException("<where>元素中不存在任何元素配置");
	}
	
	@Override
	public String getNodeName() {
		return "where";
	}
}
