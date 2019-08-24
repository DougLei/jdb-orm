package com.douglei.orm.configuration.impl.xml.element.environment.mapping.sql.validate.content.node.impl;

import org.w3c.dom.Node;

import com.douglei.orm.configuration.impl.xml.element.environment.mapping.sql.validate.content.node.SqlNodeHandler;
import com.douglei.orm.context.xml.MappingXmlConfigContext;
import com.douglei.orm.core.metadata.sql.content.node.SqlNode;
import com.douglei.tools.utils.StringUtil;

/**
 * 
 * @author DougLei
 */
public class IncludeSqlNodeHandler implements SqlNodeHandler {

	@Override
	public SqlNode doHandler(Node node) {
		String refName = getAttributeValue(node.getAttributes().getNamedItem("ref-name"));
		if(refName == null || StringUtil.isEmpty(refName)) {
			throw new NullPointerException("<include>元素必须配置ref-name属性值");
		}
		
		MappingXmlConfigContext.getSqlContentByName(refName);
		
		return null;
	}

	@Override
	public String getNodeName() {
		return "include";
	}

}
