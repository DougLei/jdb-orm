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
	private String suffix;
	
	private String[] prefixoverride;
	private String[] suffixoverride;
	
	public TrimSqlNode(String prefix, String suffix, String prefixoverride, String suffixoverride) {
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
	
	@Override
	public boolean matching(Map<String, Object> sqlParameterMap) {
		return true;
	}
	
	@Override
	public ExecuteSqlNode getExecuteSqlNode(Map<String, Object> sqlParameterMap) {
		StringBuilder sqlContent = new StringBuilder();
		List<Object> parameters = null;
		
		ExecuteSqlNode executeSqlNode = null;
		for (SqlNode sqlNode : sqlNodes) {
			if(sqlNode.matching(sqlParameterMap)) {
				executeSqlNode = sqlNode.getExecuteSqlNode(sqlParameterMap);
				if(executeSqlNode.existsParameter()) {
					if(parameters == null) {
						parameters = new ArrayList<Object>();
					}
					parameters.addAll(executeSqlNode.getParameters());
				}
				sqlContent.append(executeSqlNode.getContent()).append(" ");
			}
		}
		return new ExecuteSqlNode(getTrimContent(sqlContent), parameters);
	}

	private String getTrimContent(StringBuilder sqlContent) {
		String trimContent = sqlContent.toString();
		if(prefixoverride != null) {
			for (String po : prefixoverride) {
				if(trimContent.startsWith(po)) {
					trimContent = trimContent.substring(0, po.length());
					break;
				}
			}
		}
		if(suffixoverride != null) {
			for (String so : suffixoverride) {
				if(trimContent.endsWith(so)) {
					trimContent = trimContent.substring(0, trimContent.length()-so.length());
					break;
				}
			}
		}
		if(prefix != null) {
			trimContent = prefix + " " + trimContent;
		}
		if(suffix != null) {
			trimContent = trimContent + " " + suffix;
		}
		return trimContent;
	}
}
