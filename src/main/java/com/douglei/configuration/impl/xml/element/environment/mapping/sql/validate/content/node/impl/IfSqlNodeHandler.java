package com.douglei.configuration.impl.xml.element.environment.mapping.sql.validate.content.node.impl;

import org.w3c.dom.Node;

import com.douglei.configuration.impl.xml.element.environment.mapping.sql.validate.content.node.SqlNodeHandler;
import com.douglei.database.metadata.sql.content.node.SqlNode;
import com.douglei.database.metadata.sql.content.node.impl.IfSqlNode;
import com.douglei.utils.StringUtil;

/**
 * 
 * @author DougLei
 */
public class IfSqlNodeHandler implements SqlNodeHandler {

	@Override
	public SqlNode doHandler(Node node) {
		String content = node.getTextContent();
		if(StringUtil.isEmpty(content)) {
			throw new NullPointerException("<if>元素中的内容不能为空");
		}
		
		String expression = getAttributeValue(node.getAttributes().getNamedItem("test"));
		if(StringUtil.isEmpty(expression)) {
			throw new NullPointerException("<if>元素中的test属性值不能为空");
		}
		return new IfSqlNode(expression, content);
	}
	
	@Override
	public String getNodeName() {
		return "if";
	}
}
