package com.douglei.orm.mapping.impl.sql.metadata.parser.content.node.impl;

import org.w3c.dom.Node;

import com.douglei.orm.mapping.impl.MappingParserContext;
import com.douglei.orm.mapping.impl.sql.metadata.content.SqlContentMetadata;
import com.douglei.orm.mapping.impl.sql.metadata.content.node.SqlNode;
import com.douglei.orm.mapping.impl.sql.metadata.content.node.impl.IncludeSqlNode;
import com.douglei.orm.mapping.impl.sql.metadata.parser.content.node.SqlNodeParser;
import com.douglei.orm.mapping.impl.sql.metadata.parser.content.node.SqlNodeParserException;
import com.douglei.orm.mapping.impl.sql.metadata.parser.content.node.SqlNodeType;
import com.douglei.tools.utils.StringUtil;

/**
 * 
 * @author DougLei
 */
public class IncludeSqlNodeParser extends SqlNodeParser {

	@Override
	public SqlNode parse(Node node) throws SqlNodeParserException {
		String refName = getAttributeValue(node.getAttributes().getNamedItem("ref-name"));
		if(refName == null || StringUtil.isEmpty(refName)) 
			throw new SqlNodeParserException("<include>元素必须配置ref-name属性值");
		
		SqlContentMetadata sqlContent = MappingParserContext.getSqlContent(refName);
		if(sqlContent == null) 
			throw new SqlNodeParserException("不存在name为"+refName+"的<sql-content>元素");
		return new IncludeSqlNode(sqlContent);
	}

	@Override
	public SqlNodeType getNodeType() {
		return SqlNodeType.INCLUDE;
	}
}
