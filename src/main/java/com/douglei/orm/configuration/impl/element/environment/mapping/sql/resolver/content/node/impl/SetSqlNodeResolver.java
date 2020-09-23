package com.douglei.orm.configuration.impl.element.environment.mapping.sql.resolver.content.node.impl;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.douglei.orm.configuration.impl.element.environment.mapping.sql.resolver.content.node.SqlNodeResolver;
import com.douglei.orm.configuration.impl.element.environment.mapping.sql.resolver.content.node.SqlNodeResolverMapping;
import com.douglei.orm.configuration.impl.element.environment.mapping.sql.resolver.content.node.SqlNodeResolvingException;
import com.douglei.orm.core.metadata.sql.content.node.SqlNode;
import com.douglei.orm.core.metadata.sql.content.node.SqlNodeType;
import com.douglei.orm.core.metadata.sql.content.node.impl.SetSqlNode;

/**
 * 
 * @author DougLei
 */
public class SetSqlNodeResolver implements SqlNodeResolver {

	@Override
	public SqlNode resolving(Node node) throws SqlNodeResolvingException {
		NodeList childrens = node.getChildNodes();
		int cl = childrens.getLength();
		if(cl == 0) {
			throw new SqlNodeResolvingException("<set>元素中不存在任何元素配置");
		}
		
		SetSqlNode setSqlNode = new SetSqlNode();
		
		SqlNode c_sn = null;
		for(int i=0;i<cl;i++) {
			c_sn = SqlNodeResolverMapping.resolving(childrens.item(i));
			if(c_sn != null) {
				if(c_sn.getType() == SqlNodeType.IF) {
					setSqlNode.addSqlNode(c_sn);
				}else {
					throw new SqlNodeResolvingException("<set>元素中, 只能使用<if>元素; 也不支持在<set>元素中, 直接编写sql语句");
				}
			}
		}
		if(setSqlNode.existsSqlNode()) {
			return setSqlNode;
		}
		throw new SqlNodeResolvingException("<set>元素中不存在任何元素配置");
	}
	
	@Override
	public String getNodeName() {
		return "set";
	}
}
