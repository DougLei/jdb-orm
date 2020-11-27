package com.douglei.orm.mapping.impl.sql.metadata.parser.content.node.impl;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.douglei.orm.mapping.impl.sql.metadata.content.node.SqlNode;
import com.douglei.orm.mapping.impl.sql.metadata.content.node.impl.IfSqlNode;
import com.douglei.orm.mapping.impl.sql.metadata.parser.content.node.SqlNodeParser;
import com.douglei.orm.mapping.impl.sql.metadata.parser.content.node.SqlNodeParserContainer;
import com.douglei.orm.mapping.impl.sql.metadata.parser.content.node.SqlNodeParserException;
import com.douglei.tools.utils.StringUtil;

/**
 * 
 * @author DougLei
 */
public class IfSqlNodeParser implements SqlNodeParser {

	@Override
	public SqlNode parse(Node node) throws SqlNodeParserException {
		NodeList childrens = node.getChildNodes();
		int cl = childrens.getLength();
		if(cl == 0) 
			throw new SqlNodeParserException("<if>元素中不存在任何元素配置");
		
		String expression = getAttributeValue(node.getAttributes().getNamedItem("test"));
		if(StringUtil.isEmpty(expression)) 
			throw new SqlNodeParserException("<if>元素中的test属性值不能为空");
		
		IfSqlNode ifSqlNode = new IfSqlNode(expression);
		
		SqlNode c_sn = null;
		for(int i=0;i<cl;i++) {
			c_sn = SqlNodeParserContainer.parse(childrens.item(i));
			if(c_sn != null) 
				ifSqlNode.addSqlNode(c_sn);
		}
		
		if(ifSqlNode.existsSqlNode()) 
			return ifSqlNode;
		throw new SqlNodeParserException("<if>元素中不存在任何元素配置");
	}
	
	@Override
	public String getNodeName() {
		return "if";
	}
}
