package com.douglei.database.metadata.sql.content.node.impl;

import java.util.Map;

import com.douglei.database.metadata.MetadataType;
import com.douglei.sessions.LocalRunDataHolder;

/**
 * 
 * @author DougLei
 */
public class IfNodeMetadata extends AbstractNodeMetadata {
	private String expression;
	
	public IfNodeMetadata(String expression, String content) {
		super(content);
		this.expression = expression;
	}

	@Override
	public boolean isMatching(Map<String, Object> sqlParameterMap) {
		return LocalRunDataHolder.getExpressionResolverHandler().resolveToBoolean(expression, sqlParameterMap);
	}

	@Override
	public MetadataType getMetadataType() {
		return MetadataType.SQL_CONTENT_IF_NODE;
	}
}
