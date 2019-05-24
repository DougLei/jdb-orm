package com.douglei.database.metadata.sql.content.node;

import java.util.ArrayList;
import java.util.List;

import com.douglei.database.metadata.sql.SqlParameterMetadata;
import com.douglei.database.sql.statement.entity.InputSqlParameter;

/**
 * 可执行的sql node
 * @author DougLei
 */
public class ExecuteSqlNode {
	
	private String content;
	private List<Object> parameters;
	
	public ExecuteSqlNode(String content, List<SqlParameterMetadata> sqlParameterByDefinedOrders, Object sqlParameter, String sqlParameterNamePrefix) {
		if(sqlParameterByDefinedOrders != null) {
			Object parameterValue = null;
			for (SqlParameterMetadata parameter : sqlParameterByDefinedOrders) {
				parameterValue = parameter.getValue(sqlParameter, sqlParameterNamePrefix);
				if(parameter.isUsePlaceholder()) {
					if(parameters == null) {
						parameters = new ArrayList<Object>();
					}
					parameters.add(new InputSqlParameter(parameterValue, parameter.getDataTypeHandler()));
				}else {
					content = content.replaceFirst("#\\{"+parameter.getName()+"\\}", parameter.getPlaceholderPrefix() + parameterValue + parameter.getPlaceholderSuffix());
				}
			}
		}
		this.content = content;
	}
	
	public ExecuteSqlNode(String finalContent, List<Object> parameters) {
		this.content = finalContent;
		this.parameters = parameters;
	}

	public String getContent() {
		return content;
	}
	
	public List<Object> getParameters(){
		return parameters;
	}
	
	public boolean existsParameter() {
		return parameters != null;
	}
}
