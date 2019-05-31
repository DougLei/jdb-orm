package com.douglei.core.metadata.sql.content.node.impl;

import java.util.ArrayList;
import java.util.List;

import com.douglei.core.metadata.sql.content.node.ExecuteSqlNode;
import com.douglei.core.metadata.sql.content.node.SqlNode;
import com.douglei.core.metadata.sql.content.node.SqlNodeType;

/**
 * 
 * @author DougLei
 */
public class TrimSqlNode extends AbstractNestingNode {
	private String prefix;
	private String suffix;
	
	private String[] prefixoverride;
	private String[] suffixoverride;
	
	public TrimSqlNode(String prefix, String suffix, String prefixoverride, String suffixoverride) {
		this.prefix = prefix==null?" ":prefix+" ";
		this.suffix = suffix==null?" ":" "+suffix;
		
		if(prefixoverride != null) {
			String[] tmp = prefixoverride.split("\\|");
			int length = tmp.length;
			this.prefixoverride = new String[length];
			for (int i = 0; i < length; i++) {
				this.prefixoverride[i] = tmp[i];
			}
		}
		if(suffixoverride != null) {
			String[] tmp = suffixoverride.split("\\|");
			int length = tmp.length;
			this.suffixoverride = new String[length];
			for (int i = 0; i < length; i++) {
				this.suffixoverride[i] = tmp[i];
			}
		}
	}
	
	@Override
	public ExecuteSqlNode getExecuteSqlNode(Object sqlParameter, String sqlParameterNamePrefix) {
		StringBuilder sqlContentBuilder = new StringBuilder();
		List<Object> parameters = null;
		
		ExecuteSqlNode executeSqlNode = null;
		for (SqlNode sqlNode : sqlNodes) {
			if(sqlNode.matching(sqlParameter)) {
				executeSqlNode = sqlNode.getExecuteSqlNode(sqlParameter);
				if(executeSqlNode.existsParameter()) {
					if(parameters == null) {
						parameters = new ArrayList<Object>();
					}
					parameters.addAll(executeSqlNode.getParameters());
				}
				sqlContentBuilder.append(executeSqlNode.getContent()).append(" ");
			}
		}
		
		String sqlContent = sqlContentBuilder.toString();
		int sqlContentLength = sqlContent.length();
		if(sqlContentLength > 0) {
			if(prefixoverride != null) {
				for (String po : prefixoverride) {
					if(po.equalsIgnoreCase(sqlContent.substring(0, po.length()))) {
						sqlContent = sqlContent.substring(po.length());
						break;
					}
				}
			}
			if(suffixoverride != null) {
				sqlContentLength--;
				for (String so : suffixoverride) {
					if(so.equalsIgnoreCase(sqlContent.substring(sqlContentLength-so.length(), sqlContentLength))) {
						sqlContent = sqlContent.substring(0, sqlContentLength-so.length());
						break;
					}
				}
			}
			sqlContent = prefix + sqlContent + suffix;
		}
		return new ExecuteSqlNode(sqlContent, parameters);
	}
	
	@Override
	public SqlNodeType getType() {
		return SqlNodeType.TRIM;
	}
}