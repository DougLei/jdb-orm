package com.douglei.orm.mapping.impl.sql.metadata.parser.content.node.impl;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.douglei.orm.mapping.impl.sql.metadata.content.node.SqlNode;
import com.douglei.orm.mapping.impl.sql.metadata.content.node.SqlNodeType;
import com.douglei.orm.mapping.impl.sql.metadata.content.node.impl.TrimSqlNode;
import com.douglei.orm.mapping.impl.sql.metadata.parser.content.node.SqlNodeParser;
import com.douglei.orm.mapping.impl.sql.metadata.parser.content.node.SqlNodeParserContainer;
import com.douglei.orm.mapping.impl.sql.metadata.parser.content.node.SqlNodeParserException;

/**
 * 
 * @author DougLei
 */
public class TrimSqlNodeParser implements SqlNodeParser {

	@Override
	public SqlNode parse(Node node) throws SqlNodeParserException {
		NodeList childrens = node.getChildNodes();
		int cl = childrens.getLength();
		if(cl == 0) 
			throw new SqlNodeParserException("<trim>元素中不存在任何sql语句");
		
		NamedNodeMap attributeMap = node.getAttributes();
		TrimSqlNode trimSqlNode = new TrimSqlNode(
				getAttributeValue(attributeMap.getNamedItem("prefix")), 
				getAttributeValue(attributeMap.getNamedItem("suffix")), 
				getAttributeValue(attributeMap.getNamedItem("prefixoverride")), 
				getAttributeValue(attributeMap.getNamedItem("suffixoverride")));
		
		SqlNode c_sn = null;
		for(int i=0;i<cl;i++) {
			c_sn = SqlNodeParserContainer.parse(childrens.item(i));
			if(c_sn != null) {
				if(c_sn.getType() == SqlNodeType.IF) {
					trimSqlNode.addSqlNode(c_sn);
				}else {
					throw new SqlNodeParserException("<trim>元素中, 只能使用<if>元素; 也不支持在<trim>元素中, 直接编写sql语句");
				}
			}
		}
		if(trimSqlNode.existsSqlNode()) 
			return trimSqlNode;
		throw new SqlNodeParserException("<trim>元素中不存在任何sql语句");
	}
	
	@Override
	public String getNodeName() {
		return "trim";
	}
}
