package com.douglei.orm.configuration.impl.element.environment.mapping.sql.resolver.content.node.impl;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.douglei.orm.configuration.impl.element.environment.mapping.sql.resolver.content.node.SqlNodeResolver;
import com.douglei.orm.configuration.impl.element.environment.mapping.sql.resolver.content.node.SqlNodeResolverMapping;
import com.douglei.orm.configuration.impl.element.environment.mapping.sql.resolver.content.node.SqlNodeResolvingException;
import com.douglei.orm.core.metadata.sql.content.node.SqlNode;
import com.douglei.orm.core.metadata.sql.content.node.SqlNodeType;
import com.douglei.orm.core.metadata.sql.content.node.impl.TrimSqlNode;

/**
 * 
 * @author DougLei
 */
public class TrimSqlNodeResolver implements SqlNodeResolver {

	@Override
	public SqlNode resolving(Node node) {
		NodeList childrens = node.getChildNodes();
		int cl = childrens.getLength();
		if(cl == 0) {
			throw new SqlNodeResolvingException("<trim>元素中不存在任何sql语句");
		}
		
		NamedNodeMap attributeMap = node.getAttributes();
		TrimSqlNode trimSqlNode = new TrimSqlNode(
				getAttributeValue(attributeMap.getNamedItem("prefix")), 
				getAttributeValue(attributeMap.getNamedItem("suffix")), 
				getAttributeValue(attributeMap.getNamedItem("prefixoverride")), 
				getAttributeValue(attributeMap.getNamedItem("suffixoverride")));
		
		SqlNode c_sn = null;
		for(int i=0;i<cl;i++) {
			c_sn = SqlNodeResolverMapping.resolving(childrens.item(i));
			if(c_sn != null) {
				if(c_sn.getType() == SqlNodeType.IF) {
					trimSqlNode.addSqlNode(c_sn);
				}else {
					throw new SqlNodeResolvingException("<trim>元素中, 只能使用<if>元素; 也不支持在<trim>元素中, 直接编写sql语句");
				}
			}
		}
		if(trimSqlNode.existsSqlNode()) {
			return trimSqlNode;
		}
		throw new SqlNodeResolvingException("<trim>元素中不存在任何sql语句");
	}
	
	@Override
	public String getNodeName() {
		return "trim";
	}
}
