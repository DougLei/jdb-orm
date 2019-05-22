package com.douglei.database.metadata.sql.content.node.impl;

import java.util.List;
import java.util.Map;

import com.douglei.database.metadata.sql.SqlParameterMetadata;
import com.douglei.database.metadata.sql.content.node.SqlNode;

/**
 * 
 * @author DougLei
 */
public class TrimSqlNode implements SqlNode {
	private String prefix;
	private String[] prefixoverride;
	
	private String suffix;
	private String[] suffixoverride;
	
	public TrimSqlNode(String prefix, String prefixoverride, String suffix, String suffixoverride) {
	}

	@Override
	public boolean isMatching(Map<String, Object> sqlParameterMap) {
		return true;
	}

	@Override
	public List<SqlParameterMetadata> getSqlParameterByDefinedOrders() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getContent() {
		return null;
	}
}
