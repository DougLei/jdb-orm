package com.douglei.orm.mapping.impl.sql.metadata.content.node.impl;

import java.util.List;

import com.douglei.orm.mapping.impl.sql.metadata.content.node.SqlNode;
import com.douglei.orm.mapping.impl.sql.metadata.content.node.SqlNodeType;

/**
 * 
 * @author DougLei
 */
public class TextSqlNode implements SqlNode {
	
	private String content;
	private List<SqlParameterNode> parameters;// sql参数, 按照配置中定义的顺序记录
	
	public TextSqlNode(String content, List<SqlParameterNode> parameters) {
		this.content = content;
		this.parameters = parameters;
	}
	
	
	@Override
	public SqlNodeType getType() {
		return SqlNodeType.TEXT;
	}
}
