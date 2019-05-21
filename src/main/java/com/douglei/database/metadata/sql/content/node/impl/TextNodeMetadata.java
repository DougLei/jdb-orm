package com.douglei.database.metadata.sql.content.node.impl;

import java.util.Map;

import com.douglei.database.metadata.MetadataType;

/**
 * 
 * @author DougLei
 */
public class TextNodeMetadata extends AbstractNodeMetadata {

	public TextNodeMetadata(String content) {
		super(content);
	}

	@Override
	public boolean isMatching(Map<String, Object> sqlParameterMap) {
		return true;
	}

	@Override
	public MetadataType getMetadataType() {
		return MetadataType.SQL_CONTENT_TEXT_NODE;
	}
}
