package com.douglei.orm.mapping.impl.sql.metadata.content.node.impl;

import java.util.ArrayList;
import java.util.List;

import com.douglei.orm.mapping.impl.sql.metadata.content.node.ExecutableSqlNode;
import com.douglei.orm.mapping.impl.sql.metadata.content.node.SqlNode;
import com.douglei.orm.mapping.impl.sql.metadata.parameter.SqlParameterMetadata;
import com.douglei.orm.mapping.validator.ValidationResult;
import com.douglei.orm.sessionfactory.sessions.session.sql.PurposeEntity;

/**
 * 嵌套node
 * @author DougLei
 */
public abstract class AbstractNestingNode implements SqlNode{
	
	protected List<SqlNode> sqlNodes;// 内部的node集合
	
	/**
	 * 添加内部node
	 * @param sqlNode
	 */
	public void addSqlNode(SqlNode sqlNode) {
		if(sqlNodes == null) 
			sqlNodes = new ArrayList<SqlNode>();
		sqlNodes.add(sqlNode);
	}
	
	/**
	 * 是否存在内部node
	 * @return
	 */
	public boolean existsSqlNode() {
		return sqlNodes != null;
	}
	
	@Override
	public ExecutableSqlNode getExecutableSqlNode(PurposeEntity purposeEntity, Object sqlParameter, String previousAlias) {
		List<String> contentList = null;
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
				
				if(contentList == null) 
					contentList = new ArrayList<String>(10);
				contentList.add(executableSqlNode.getContent());
			}
		}
		
		if(contentList == null) 
			return ExecutableSqlNode.emptyExecutableSqlNode();
		
		StringBuilder sqlContent = new StringBuilder(100);
		for (String content : contentList) 
			sqlContent.append(content).append(' ');
		
		return new ExecutableSqlNode(sqlContent.toString(), parameters, parameterValues);
	}

	@Override
	public ValidationResult validateParameter(Object sqlParameter, String previousAlias) {
		ValidationResult result = null;
		for (SqlNode sqlNode : sqlNodes) {
			if(sqlNode.matching(sqlParameter, previousAlias) && (result = sqlNode.validateParameter(sqlParameter, previousAlias)) != null) 
				return result;
		}
		return null;
	}
}
