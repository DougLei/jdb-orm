package com.douglei.configuration.impl.xml.element.environment.mapping.sql.validate.content.node.impl;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.douglei.configuration.impl.xml.element.environment.mapping.sql.validate.content.node.SqlNodeHandler;
import com.douglei.configuration.impl.xml.element.environment.mapping.sql.validate.content.node.SqlNodeHandlerMapping;
import com.douglei.configuration.impl.xml.element.environment.mapping.sql.validate.content.node.SqlNodeMismatchingException;
import com.douglei.database.metadata.sql.content.node.SqlNode;
import com.douglei.database.metadata.sql.content.node.SqlNodeType;
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
		
		SqlNode c_sn = null;
		for(int i=0;i<cl;i++) {
			c_sn = SqlNodeHandlerMapping.doHandler(childrens.item(i));
			if(c_sn != null) {
				if(c_sn.getType() == SqlNodeType.IF) {
					setSqlNode.addSqlNode(c_sn);
				}else {
					throw new SqlNodeMismatchingException("<set>元素中, 只能使用<if>元素; 也不支持在<set>元素中, 直接编写sql语句");
				}
			}
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
