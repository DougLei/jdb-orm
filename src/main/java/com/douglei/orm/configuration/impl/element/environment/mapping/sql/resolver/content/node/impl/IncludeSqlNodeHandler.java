package com.douglei.orm.configuration.impl.element.environment.mapping.sql.resolver.content.node.impl;

import org.w3c.dom.Node;

import com.douglei.orm.configuration.impl.element.environment.mapping.MappingResolverContext;
import com.douglei.orm.configuration.impl.element.environment.mapping.sql.resolver.content.node.SqlNodeHandler;
import com.douglei.orm.core.metadata.sql.SqlContentMetadata;
import com.douglei.orm.core.metadata.sql.content.node.SqlNode;
import com.douglei.orm.core.metadata.sql.content.node.impl.IncludeSqlNode;
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
		
		SqlContentMetadata sqlContent = MappingResolverContext.getSqlContent(refName);
		if(sqlContent == null) {
			throw new NullPointerException("不存在name=["+refName+"]的<sql-content>元素");
		}
		return new IncludeSqlNode(sqlContent);
	}

	@Override
	public String getNodeName() {
		return "include";
	}
}
