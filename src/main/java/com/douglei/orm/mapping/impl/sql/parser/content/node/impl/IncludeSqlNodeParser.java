package com.douglei.orm.mapping.impl.sql.parser.content.node.impl;

import org.w3c.dom.Node;

import com.douglei.orm.mapping.MappingParseToolContext;
import com.douglei.orm.mapping.impl.sql.metadata.content.node.SqlNode;
import com.douglei.orm.mapping.impl.sql.metadata.content.node.SqlNodeType;
import com.douglei.orm.mapping.impl.sql.metadata.content.node.impl.IncludeSqlNode;
import com.douglei.orm.mapping.impl.sql.parser.content.node.SqlNodeParser;
import com.douglei.orm.mapping.impl.sql.parser.content.node.SqlNodeParserException;
import com.douglei.tools.StringUtil;

/**
 * 
 * @author DougLei
 */
public class IncludeSqlNodeParser extends SqlNodeParser {

	@Override
	public SqlNode parse(Node node) throws SqlNodeParserException {
		String refName = getAttributeValue(node.getAttributes().getNamedItem("ref-name"));
		if(refName == null || StringUtil.isEmpty(refName)) 
			throw new SqlNodeParserException("<include>中必须配置ref-name属性值");
		
		if(MappingParseToolContext.getMappingParseTool().getSqlContent(refName) == null) 
			throw new SqlNodeParserException("不存在name为"+refName+"的<sql-content>");
		return new IncludeSqlNode(refName);
	}

	@Override
	public SqlNodeType getNodeType() {
		return SqlNodeType.INCLUDE;
	}
}
