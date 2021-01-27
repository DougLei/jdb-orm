package com.douglei.orm.sessionfactory.sessions.session.sql.impl;

import java.util.ArrayList;
import java.util.List;

import com.douglei.orm.mapping.impl.sql.metadata.content.ContentMetadata;
import com.douglei.orm.mapping.impl.sql.metadata.content.ContentType;
import com.douglei.orm.mapping.impl.sql.metadata.content.IncrementIdValueConfig;
import com.douglei.orm.mapping.impl.sql.metadata.content.node.ExecutableSqlNode;
import com.douglei.orm.mapping.impl.sql.metadata.content.node.SqlNode;
import com.douglei.orm.mapping.impl.sql.metadata.parameter.SqlParameterMetadata;
import com.douglei.orm.sessionfactory.sessions.session.sql.PurposeEntity;

/**
 * 可执行的sql
 * @author DougLei
 */
public class ExecutableSql {
	private String name;
	private ContentType type;
	private String content;
	private List<SqlParameterMetadata> parameters; // sql参数集合
	private List<Object> parameterValues; // 执行sql语句时的参数值集合
	private IncrementIdValueConfig incrementIdValueConfig; // 自增id值的配置, 用在insert类型的sql语句
	
	public ExecutableSql(PurposeEntity purposeEntity, ContentMetadata contentMetadata, Object sqlParameter) {
		StringBuilder sqlContent = new StringBuilder();
		
		ExecutableSqlNode rootExecutableSqlNode = null;
		for (SqlNode sqlNode : contentMetadata.getSqlNodes()) {
			if(sqlNode.matching(sqlParameter)) {
				rootExecutableSqlNode = sqlNode.getExecutableSqlNode(purposeEntity, sqlParameter);
				if(rootExecutableSqlNode.existsParameters()) {
					if(parameters == null) 
						parameters = new ArrayList<SqlParameterMetadata>();
					parameters.addAll(rootExecutableSqlNode.getParameters());
				}
				if(rootExecutableSqlNode.existsParameterValues()) {
					if(parameterValues == null)
						parameterValues = new ArrayList<Object>();
					parameterValues.addAll(rootExecutableSqlNode.getParameterValues());
				}
				sqlContent.append(rootExecutableSqlNode.getContent()).append(" ");
			}
		}
		
		// 记录sql语句
		this.content = sqlContent.toString();
		this.name = contentMetadata.getName();
		this.type = contentMetadata.getType();
		this.incrementIdValueConfig = contentMetadata.getIncrementIdValueConfig();
	}
	
	public String getName() {
		return name;
	}
	public ContentType getType() {
		return type;
	}
	public String getContent() {
		return content;
	}
	public List<SqlParameterMetadata> getParameters() {
		return parameters;
	}
	public List<Object> getParameterValues() {
		return parameterValues;
	}
	public IncrementIdValueConfig getIncrementIdValueConfig() {
		return incrementIdValueConfig;
	}
}
