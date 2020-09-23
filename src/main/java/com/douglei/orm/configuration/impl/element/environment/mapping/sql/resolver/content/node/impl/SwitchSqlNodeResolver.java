package com.douglei.orm.configuration.impl.element.environment.mapping.sql.resolver.content.node.impl;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.douglei.orm.configuration.impl.element.environment.mapping.sql.resolver.content.node.SqlNodeResolver;
import com.douglei.orm.configuration.impl.element.environment.mapping.sql.resolver.content.node.SqlNodeResolverMapping;
import com.douglei.orm.configuration.impl.element.environment.mapping.sql.resolver.content.node.SqlNodeResolvingException;
import com.douglei.orm.core.metadata.sql.content.node.SqlNode;
import com.douglei.orm.core.metadata.sql.content.node.SqlNodeType;
import com.douglei.orm.core.metadata.sql.content.node.impl.SwitchSqlNode;

/**
 * 
 * @author DougLei
 */
public class SwitchSqlNodeResolver implements SqlNodeResolver {

	@Override
	public SqlNode resolving(Node node) throws SqlNodeResolvingException {
		NodeList childrens = node.getChildNodes();
		int cl = childrens.getLength();
		if(cl == 0) {
			throw new SqlNodeResolvingException("<switch>元素中不存在任何元素配置");
		}
		
		SwitchSqlNode switchSqlNode = new SwitchSqlNode();
		
		SqlNode c_sn = null;
		for(int i=0;i<cl;i++) {
			c_sn = SqlNodeResolverMapping.resolving(childrens.item(i));
			if(c_sn != null) {
				if(c_sn.getType() == SqlNodeType.IF || c_sn.getType() == SqlNodeType.ELSE) {
					switchSqlNode.addSqlNode(c_sn);
				}else {
					throw new SqlNodeResolvingException("<switch>元素中, 只能使用<if>元素或<else>元素; 也不支持在<switch>元素中, 直接编写sql语句");
				}
			}
		}
		if(switchSqlNode.existsSqlNode()) {
			return switchSqlNode;
		}
		throw new SqlNodeResolvingException("<switch>元素中不存在任何元素配置");
	}
	
	@Override
	public String getNodeName() {
		return "switch";
	}
}
