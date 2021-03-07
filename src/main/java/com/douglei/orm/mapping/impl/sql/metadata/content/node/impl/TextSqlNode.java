package com.douglei.orm.mapping.impl.sql.metadata.content.node.impl;

import java.util.List;

import com.douglei.orm.mapping.impl.sql.SqlNodeType;
import com.douglei.orm.mapping.impl.sql.metadata.content.node.SqlNode;

/**
 * 
 * @author DougLei
 */
public class TextSqlNode implements SqlNode {
	private String sql;
	private List<ParameterNode> parameters;// sql参数, 按照配置中定义的顺序记录
	
	public TextSqlNode(String sql, List<ParameterNode> parameters) {
		this.sql = sql;
		this.parameters = parameters;
	}
	
	@Override
	public SqlNodeType getType() {
		return SqlNodeType.TEXT;
	}

	public String getSql() {
		return sql;
	}
	public List<ParameterNode> getParameters() {
		return parameters;
	}
}
