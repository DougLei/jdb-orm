package com.douglei.orm.mapping.impl.sql.metadata.content.node.impl;

import java.util.ArrayList;
import java.util.List;

import com.douglei.orm.mapping.impl.sql.metadata.content.node.ExecuteSqlNode;
import com.douglei.orm.mapping.impl.sql.metadata.content.node.SqlNode;
import com.douglei.orm.mapping.impl.sql.metadata.content.node.SqlNodeType;
import com.douglei.orm.mapping.impl.sql.metadata.parameter.SqlParameterMetadata;
import com.douglei.orm.mapping.metadata.validator.ValidationResult;
import com.douglei.orm.sessionfactory.sessions.session.sql.PurposeEntity;
import com.douglei.tools.instances.ognl.OgnlHandler;

/**
 * 
 * @author DougLei
 */
public class IfSqlNode extends AbstractNestingNode {
	private static final long serialVersionUID = -4575393350930688205L;
	
	private String expression;
	
	public IfSqlNode(String expression) {
		this.expression = expression;
	}

	@Override
	public boolean matching(Object sqlParameter, String alias) {
		processExpression(alias);
		if(sqlParameter == null)
			return false;
		return OgnlHandler.getSingleton().getBooleanValue(expression, sqlParameter);
	}
	
	private boolean flag;// 是否处理了expression, 默认没有处理
	private void processExpression(String alias) {
		if(!flag) {
			flag = true;
			if(alias != null && expression.indexOf(alias+".") != -1) 
				expression = expression.replace(alias+".", "");
		}
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
		for (String sc : sqlContentList) {
			sqlContent.append(sc).append(' ');
		}
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
	
	@Override
	public SqlNodeType getType() {
		return SqlNodeType.IF;
	}
}
