package com.douglei.core.metadata.sql.content.node.impl;

import java.util.List;

import com.douglei.core.metadata.sql.SqlParameterMetadata;
import com.douglei.core.metadata.sql.content.node.SqlNodeType;

/**
 * 
 * @author DougLei
 */
public class TextSqlNode extends AbstractSqlNode {

	public TextSqlNode(String content) {
		super(content);
	}

	@Override
	public SqlNodeType getType() {
		return SqlNodeType.TEXT;
	}
	
	public String getContent() {
		return content;
	}
	public List<SqlParameterMetadata> getSqlParameterByDefinedOrders(){
		return sqlParameterByDefinedOrders;
	}
}
