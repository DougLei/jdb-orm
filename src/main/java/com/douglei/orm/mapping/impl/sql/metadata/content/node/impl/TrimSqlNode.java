package com.douglei.orm.mapping.impl.sql.metadata.content.node.impl;

import java.util.ArrayList;
import java.util.List;

import com.douglei.orm.mapping.impl.sql.metadata.content.node.ExecutableSqlNode;
import com.douglei.orm.mapping.impl.sql.metadata.content.node.SqlNode;
import com.douglei.orm.mapping.impl.sql.metadata.parameter.SqlParameterMetadata;
import com.douglei.orm.mapping.impl.sql.metadata.parser.content.node.SqlNodeType;
import com.douglei.orm.sessionfactory.sessions.session.sql.PurposeEntity;

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
		return new ExecutableSqlNode(sqlContent, parameters, parameterValues);
	}
}