package com.douglei.orm.mapping.impl.sql.metadata.content.node.impl;

import java.util.ArrayList;
import java.util.List;

import com.douglei.orm.mapping.impl.sql.metadata.content.node.ExecutableSqlNode;
import com.douglei.orm.mapping.impl.sql.metadata.content.node.SqlNode;
import com.douglei.orm.mapping.impl.sql.metadata.parameter.SqlParameterMetadata;
import com.douglei.orm.mapping.impl.sql.parser.content.node.SqlNodeType;
import com.douglei.orm.sessionfactory.sessions.session.sql.PurposeEntity;

/**
 * 
 * @author DougLei
 */
public class TrimSqlNode extends AbstractNestingNode {
	
	private String prefix;
	private String suffix;
	
	private String[] prefixOverride;
	private String[] suffixOverride;
	
	public TrimSqlNode(String prefix, String suffix, String[] prefixOverride, String[] suffixOverride) {
		this.prefix = prefix;
		this.suffix = suffix;
		this.prefixOverride = prefixOverride;
		this.suffixOverride = suffixOverride;
	}
	
	@Override
	public SqlNodeType getType() {
		return SqlNodeType.TRIM;
	}
	
	@Override
	public ExecutableSqlNode getExecutableSqlNode(PurposeEntity purposeEntity, Object sqlParameter, String previousAlias) {
		StringBuilder sqlContentBuilder = new StringBuilder();
		List<SqlParameterMetadata> parameters = null;
		List<Object> parameterValues = null;
		
		ExecutableSqlNode executableSqlNode = null;
		for (SqlNode sqlNode : sqlNodes) {
			if(sqlNode.matching(sqlParameter, previousAlias)) {
				executableSqlNode = sqlNode.getExecutableSqlNode(purposeEntity, sqlParameter, previousAlias);
				if(executableSqlNode.existsParameters()) {
					if(parameters == null)
						parameters = new ArrayList<SqlParameterMetadata>();
					parameters.addAll(executableSqlNode.getParameters());
				}
				if(executableSqlNode.existsParameterValues()) {
					if(parameterValues == null) 
						parameterValues = new ArrayList<Object>();
					parameterValues.addAll(executableSqlNode.getParameterValues());
				}
				sqlContentBuilder.append(executableSqlNode.getContent()).append(" ");
			}
		}
		
		String sqlContent = sqlContentBuilder.toString();
		int sqlContentLength = sqlContent.length();
		if(sqlContentLength > 0) {
			if(prefixOverride != null) {
				for (String po : prefixOverride) {
					if(po.equalsIgnoreCase(sqlContent.substring(0, po.length()))) {
						sqlContent = sqlContent.substring(po.length());
						break;
					}
				}
			}
			if(suffixOverride != null) {
				sqlContentLength--;
				for (String so : suffixOverride) {
					if(so.equalsIgnoreCase(sqlContent.substring(sqlContentLength-so.length(), sqlContentLength))) {
						sqlContent = sqlContent.substring(0, sqlContentLength-so.length());
						break;
					}
				}
			}
			sqlContent = prefix + sqlContent + suffix;
		}
		return new ExecutableSqlNode(sqlContent, parameters, parameterValues);
	}
}