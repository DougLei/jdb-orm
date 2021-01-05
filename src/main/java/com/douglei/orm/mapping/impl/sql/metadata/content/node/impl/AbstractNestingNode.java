package com.douglei.orm.mapping.impl.sql.metadata.content.node.impl;

import java.util.ArrayList;
import java.util.List;

import com.douglei.orm.mapping.impl.sql.metadata.content.node.ExecuteSqlNode;
import com.douglei.orm.mapping.impl.sql.metadata.content.node.SqlNode;
import com.douglei.orm.mapping.impl.sql.metadata.parameter.SqlParameterMetadata;
import com.douglei.orm.mapping.metadata.validator.ValidationResult;
import com.douglei.orm.sessionfactory.sessions.session.sql.PurposeEntity;

/**
 * 嵌套node
 * @author DougLei
 */
public abstract class AbstractNestingNode implements SqlNode{
	private static final long serialVersionUID = -7977542480508156574L;
	
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
	public ExecuteSqlNode getExecuteSqlNode(PurposeEntity purposeEntity, Object sqlParameter, String previousAlias) {
		List<String> contentList = null;
		List<SqlParameterMetadata> parameters = null;
		List<Object> parameterValues = null;
		
		ExecuteSqlNode executeSqlNode = null;
		for (SqlNode sqlNode : sqlNodes) {
			if(sqlNode.matching(sqlParameter, previousAlias)) {
				executeSqlNode = sqlNode.getExecuteSqlNode(purposeEntity, sqlParameter, previousAlias);
				if(executeSqlNode.existsParameters()) {
					if(parameters == null)
						parameters = new ArrayList<SqlParameterMetadata>();
					parameters.addAll(executeSqlNode.getParameters());
				}
				if(executeSqlNode.existsParameterValues()) {
					if(parameterValues == null) 
						parameterValues = new ArrayList<Object>();
					parameterValues.addAll(executeSqlNode.getParameterValues());
				}
				
				if(contentList == null) 
					contentList = new ArrayList<String>(10);
				contentList.add(executeSqlNode.getContent());
			}
		}
		
		if(contentList == null) 
			return ExecuteSqlNode.emptyExecuteSqlNode();
		
		StringBuilder sqlContent = new StringBuilder(100);
		for (String content : contentList) 
			sqlContent.append(content).append(' ');
		
		return new ExecuteSqlNode(sqlContent.toString(), parameters, parameterValues);
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
