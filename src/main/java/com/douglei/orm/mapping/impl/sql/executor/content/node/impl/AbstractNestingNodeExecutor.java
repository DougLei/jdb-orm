package com.douglei.orm.mapping.impl.sql.executor.content.node.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.douglei.orm.mapping.impl.sql.SqlNodeContainer;
import com.douglei.orm.mapping.impl.sql.executor.content.node.ExecutableSqlNode;
import com.douglei.orm.mapping.impl.sql.executor.content.node.SqlNodeExecutor;
import com.douglei.orm.mapping.impl.sql.metadata.content.SqlContentMetadata;
import com.douglei.orm.mapping.impl.sql.metadata.content.node.SqlNode;
import com.douglei.orm.mapping.impl.sql.metadata.content.node.impl.AbstractNestingNode;
import com.douglei.orm.mapping.impl.sql.metadata.content.node.impl.ParameterNode;
import com.douglei.orm.mapping.validator.ValidateFailResult;
import com.douglei.orm.mapping.validator.Validator;
import com.douglei.orm.sessionfactory.sessions.session.sql.purpose.PurposeEntity;

/**
 * 嵌套node
 * @author DougLei
 */
public abstract class AbstractNestingNodeExecutor<T extends AbstractNestingNode> {
	
	/**
	 * 
	 * @param purposeEntity
	 * @param currentSqlNode
	 * @param sqlContentMetadataMap
	 * @param sqlParameter
	 * @param previousAlias
	 * @return
	 */
	public ExecutableSqlNode getExecutableSqlNode(PurposeEntity purposeEntity, T currentSqlNode, Map<String, SqlContentMetadata> sqlContentMetadataMap, Object sqlParameter, String previousAlias) {
		List<String> sqls = null;
		List<ParameterNode> parameters = null;
		List<Object> parameterValues = null;
		
		for (SqlNode sqlNode : currentSqlNode.getSqlNodes()) {
			SqlNodeExecutor<SqlNode> executor = SqlNodeContainer.getExecutor(sqlNode);
			if(executor.matching(sqlNode, sqlParameter, previousAlias)) {
				ExecutableSqlNode executableSqlNode = executor.getExecutableSqlNode(purposeEntity, sqlNode, sqlContentMetadataMap, sqlParameter);
				if(executableSqlNode.getParameters() != null) {
					if(parameters == null)
						parameters = new ArrayList<ParameterNode>();
					parameters.addAll(executableSqlNode.getParameters());
				}
				if(executableSqlNode.getParameterValues() != null) {
					if(parameterValues == null) 
						parameterValues = new ArrayList<Object>();
					parameterValues.addAll(executableSqlNode.getParameterValues());
				}
				
				if(sqls == null) 
					sqls = new ArrayList<String>();
				sqls.add(executableSqlNode.getSql());
			}
		}
		
		if(sqls == null) 
			return ExecutableSqlNode.EMPTY_EXECUTABLE_SQL_NODE;
		
		StringBuilder sql = new StringBuilder(100);
		for (String s : sqls) 
			sql.append(s).append(' ');
		return new ExecutableSqlNode(sql.toString(), parameters, parameterValues);
	}

	/**
	 * 
	 * @param currentSqlNode
	 * @param sqlContentMetadataMap
	 * @param validatorsMap
	 * @param sqlParameter
	 * @param previousAlias
	 * @return
	 */
	public ValidateFailResult validate(T currentSqlNode, Map<String, SqlContentMetadata> sqlContentMetadataMap, Map<String, List<Validator>> validatorsMap, Object sqlParameter, String previousAlias) {
		for (SqlNode sqlNode : currentSqlNode.getSqlNodes()) {
			SqlNodeExecutor<SqlNode> executor = SqlNodeContainer.getExecutor(sqlNode);
			if(executor.matching(sqlNode, sqlParameter, previousAlias)) {
				ValidateFailResult failResult = executor.validate(sqlNode, sqlContentMetadataMap, validatorsMap, sqlParameter, previousAlias);
				if(failResult != null)
					return failResult;
			}
		}
		return null;
	}
}
