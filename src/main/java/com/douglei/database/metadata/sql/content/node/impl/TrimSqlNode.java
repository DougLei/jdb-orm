package com.douglei.database.metadata.sql.content.node.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.douglei.database.metadata.sql.content.node.ExecuteSqlNode;
import com.douglei.database.metadata.sql.content.node.SqlNode;
import com.douglei.utils.StringUtil;

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
		this.prefix = prefix;
		this.suffix = suffix;
		if(StringUtil.notEmpty(prefixoverride)) {
			this.prefixoverride = prefixoverride.split("|");
		}
		if(StringUtil.notEmpty(suffixoverride)) {
			this.suffixoverride = suffixoverride.split("|");
		}
	}
	
	private List<SqlNode> sqlNodes;
	public void addSqlNode(SqlNode sqlNode) {
		if(sqlNode == null) {
			return;
		}
		if(sqlNodes == null) {
			sqlNodes = new ArrayList<SqlNode>();
		}
		sqlNodes.add(sqlNode);
	}
	
	private Map<String, Object> sqlParameterMap;
	@Override
	public boolean isMatching(Map<String, Object> sqlParameterMap) {
		this.sqlParameterMap = sqlParameterMap;
		return true;
	}
	
	@Override
	public ExecuteSqlNode getExecuteSqlNode(Map<String, Object> sqlParameterMap) {
		// TODO Auto-generated method stub
		return null;
	}
}
