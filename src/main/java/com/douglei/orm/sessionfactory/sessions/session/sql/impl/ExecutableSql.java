package com.douglei.orm.sessionfactory.sessions.session.sql.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.douglei.orm.mapping.impl.sql.metadata.content.ContentMetadata;
import com.douglei.orm.mapping.impl.sql.metadata.content.ContentType;
import com.douglei.orm.mapping.impl.sql.metadata.content.SqlContentMetadata;
import com.douglei.orm.mapping.impl.sql.SqlNodeContainer;
import com.douglei.orm.mapping.impl.sql.executor.content.node.ExecutableSqlNode;
import com.douglei.orm.mapping.impl.sql.executor.content.node.SqlNodeExecutor;
import com.douglei.orm.mapping.impl.sql.metadata.content.AutoIncrementIDMetadata;
import com.douglei.orm.mapping.impl.sql.metadata.content.node.SqlNode;
import com.douglei.orm.mapping.impl.sql.metadata.content.node.impl.ParameterNode;
import com.douglei.orm.sessionfactory.sessions.session.sql.purpose.PurposeEntity;

/**
 * 可执行的sql
 * @author DougLei
 */
public class ExecutableSql {
	private String sql;
	private String name;
	private ContentType type;
	private List<ParameterNode> parameters; // sql参数集合
	private List<Object> parameterValues; // 执行sql语句时的参数值集合
	private AutoIncrementIDMetadata autoIncrementID; // 自增id值的配置, 用在insert类型的sql语句
	
	public ExecutableSql(PurposeEntity purposeEntity, ContentMetadata contentMetadata, Map<String, SqlContentMetadata> sqlContentMetadataMap, Object sqlParameter) {
		StringBuilder sql = new StringBuilder();
		for (SqlNode sqlNode : contentMetadata.getSqlNodes()) {
			SqlNodeExecutor<SqlNode> executor = SqlNodeContainer.getExecutor(sqlNode);
			if(executor.matching(sqlNode, sqlParameter)) {
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
				sql.append(executableSqlNode.getSql()).append(" ");
			}
		}
		
		this.sql = sql.toString();
		this.name = contentMetadata.getName();
		this.type = contentMetadata.getType();
		this.autoIncrementID = contentMetadata.getAutoIncrementID();
	}
	
	public String getSql() {
		return sql;
	}
	public String getName() {
		return name;
	}
	public ContentType getType() {
		return type;
	}
	public List<ParameterNode> getParameters() {
		return parameters;
	}
	public List<Object> getParameterValues() {
		return parameterValues;
	}
	public AutoIncrementIDMetadata getAutoIncrementID() {
		return autoIncrementID;
	}
}
