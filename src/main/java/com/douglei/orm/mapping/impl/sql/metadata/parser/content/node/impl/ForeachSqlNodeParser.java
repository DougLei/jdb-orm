package com.douglei.orm.mapping.impl.sql.metadata.parser.content.node.impl;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.douglei.orm.mapping.impl.sql.metadata.content.node.SqlNode;
import com.douglei.orm.mapping.impl.sql.metadata.content.node.impl.ForeachSqlNode;
import com.douglei.orm.mapping.impl.sql.metadata.parser.content.node.SqlNodeParser;
import com.douglei.orm.mapping.impl.sql.metadata.parser.content.node.SqlNodeParserContainer;
import com.douglei.orm.mapping.impl.sql.metadata.parser.content.node.SqlNodeParserException;
import com.douglei.tools.utils.StringUtil;

/**
 * 
 * @author DougLei
 */
public class ForeachSqlNodeParser implements SqlNodeParser {

	@Override
	public SqlNode parse(Node node) throws SqlNodeParserException {
		NodeList childrens = node.getChildNodes();
		int cl = childrens.getLength();
		if(cl == 0) 
			throw new SqlNodeParserException("<foreach>元素中不存在任何sql语句");
		
		NamedNodeMap attributeMap = node.getAttributes();
		
		String collection = getAttributeValue(attributeMap.getNamedItem("collection"));
		if(StringUtil.isEmpty(collection)) {
			throw new SqlNodeParserException("<foreach>元素中的collection属性值不能为空");
		}
		
		String alias = getAttributeValue(attributeMap.getNamedItem("alias"));
		if(StringUtil.isEmpty(alias)) 
			throw new SqlNodeParserException("<foreach>元素中的alias属性值不能为空");
		
		ForeachSqlNode foreachSqlNode = new ForeachSqlNode(collection.trim(), alias.trim(),
				getAttributeValue(attributeMap.getNamedItem("open")),
				getAttributeValue(attributeMap.getNamedItem("separator")),
				getAttributeValue(attributeMap.getNamedItem("close")));
		
		SqlNode sqlNode = null;
		for(int i=0;i<cl;i++) {
			sqlNode = SqlNodeParserContainer.parse(childrens.item(i));
			if(sqlNode != null) 
				foreachSqlNode.addSqlNode(sqlNode);
		}
		if(foreachSqlNode.existsSqlNode()) 
			return foreachSqlNode;
		throw new SqlNodeParserException("<foreach>元素中不存在任何sql语句");
	}
	
	@Override
	public String getNodeName() {
		return "foreach";
	}
}
