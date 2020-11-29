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
	public ExecuteSqlNode getExecuteSqlNode(PurposeEntity purposeEntity, Object sqlParameter, String alias) {
		List<String> sqlContentList = null;
		List<Object> parameters = null;
		List<SqlParameterMetadata> sqlParameters = null;
		
		ExecuteSqlNode executeSqlNode = null;
		for (SqlNode sqlNode : sqlNodes) {
			if(sqlNode.matching(sqlParameter, alias)) {
				executeSqlNode = sqlNode.getExecuteSqlNode(purposeEntity, sqlParameter, alias);
				if(executeSqlNode.existsParameters()) {
					if(parameters == null) 
						parameters = new ArrayList<Object>();
					parameters.addAll(executeSqlNode.getParameters());
				}
				if(executeSqlNode.existsSqlParameters()) {
					if(sqlParameters == null)
						sqlParameters = new ArrayList<SqlParameterMetadata>();
					sqlParameters.addAll(executeSqlNode.getSqlParameters());
				}
				
				if(sqlContentList == null) 
					sqlContentList = new ArrayList<String>(10);
				sqlContentList.add(executeSqlNode.getContent());
			}
		}
		
		if(sqlContentList == null) 
			return ExecuteSqlNode.emptyExecuteSqlNode();
		
		StringBuilder sqlContent = new StringBuilder(100);
		for (String sc : sqlContentList) 
			sqlContent.append(sc).append(' ');
		
		return new ExecuteSqlNode(sqlContent.toString(), parameters, sqlParameters);
	}

	@Override
	public ValidationResult validateParameter(Object sqlParameter, String alias) {
		ValidationResult result = null;
		for (SqlNode sqlNode : sqlNodes) {
			if(sqlNode.matching(sqlParameter, alias) && (result = sqlNode.validateParameter(sqlParameter, alias)) != null) 
				return result;
		}
		return null;
	}
}
