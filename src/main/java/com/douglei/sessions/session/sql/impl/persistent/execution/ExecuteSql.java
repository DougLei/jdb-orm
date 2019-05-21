package com.douglei.sessions.session.sql.impl.persistent.execution;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.douglei.database.metadata.sql.SqlContentMetadata;
import com.douglei.database.metadata.sql.SqlParameterMetadata;
import com.douglei.database.metadata.sql.content.node.NodeMetadata;
import com.douglei.database.sql.statement.entity.InputSqlParameter;

/**
 * 要执行的sql实体
 * @author DougLei
 */
public class ExecuteSql {
	private String content;
	private List<Object> parameters;
	
	public ExecuteSql(SqlContentMetadata contentMetadata, Map<String, Object> sqlParameterMap) {
		StringBuilder sqlContent = new StringBuilder();
		
		List<NodeMetadata> nodes = contentMetadata.getNodes();
		
		String tmpContent = null;
		List<SqlParameterMetadata> parametersInNode = null;
		Object parameterValue = null;
		for (NodeMetadata node : nodes) {
			if(node.isMatching(sqlParameterMap)) {
				tmpContent = node.getContent();
				parametersInNode = node.getSqlParameterByDefinedOrders();
				
				if(parametersInNode != null) {
					if(parameters == null) {
						parameters = new ArrayList<Object>();
					}
					
					for (SqlParameterMetadata parameter : parametersInNode) {
						parameterValue = parameter.getValue(sqlParameterMap);
						if(parameter.isUsePlaceholder()) {
							parameters.add(new InputSqlParameter(parameterValue, parameter.getDataTypeHandler()));
						}else {
							tmpContent = tmpContent.replaceFirst("\\$\\{"+parameter.getName()+"\\}", parameter.getPlaceholderPrefix() + parameterValue + parameter.getPlaceholderSuffix());
						}
					}
				}
				sqlContent.append(tmpContent).append(" ");
			}
		}
		
		// 记录sql语句
		this.content = sqlContent.toString();
	}
	
	public String getContent() {
		return content;
	}
	public List<Object> getParameters() {
		return parameters;
	}
}
