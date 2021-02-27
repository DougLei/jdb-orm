package com.douglei.orm.mapping.impl.sql.metadata.content.node.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import com.douglei.orm.configuration.environment.EnvironmentContext;
import com.douglei.orm.configuration.environment.mapping.SqlMappingConfiguration;
import com.douglei.orm.configuration.environment.mapping.SqlMappingParameterPSRelation;
import com.douglei.orm.mapping.impl.sql.metadata.content.node.ExecutableSqlNode;
import com.douglei.orm.mapping.impl.sql.metadata.content.node.SqlNode;
import com.douglei.orm.mapping.impl.sql.metadata.parameter.SqlParameterMetadata;
import com.douglei.orm.mapping.impl.sql.parser.content.node.SqlNodeParserException;
import com.douglei.orm.mapping.impl.sql.parser.content.node.SqlNodeType;
import com.douglei.orm.sessionfactory.sessions.session.sql.PurposeEntity;

/**
 * 
 * @author DougLei
 */
public class TextSqlNode implements SqlNode {
	
	private String content;
	private List<SqlParameterMetadata> parameters;// sql参数, 按照配置中定义的顺序记录
	
	public TextSqlNode(String content, List<SqlParameterMetadata> parameters) {
		this.content = content;
		this.parameters = parameters;
	}
	
	
	@Override
	public SqlNodeType getType() {
		return SqlNodeType.TEXT;
	}
	
	@Override
	public ExecutableSqlNode getExecutableSqlNode(PurposeEntity purposeEntity, Object sqlParameter, String alias) {
		return new ExecutableSqlNode(purposeEntity, content, parameters, sqlParameter, alias);
	}

	@Override
	public ValidationResult validateParameter(Object sqlParameter, String alias) {
		if(parameters != null) {
			ValidationResult result = null;
			for (SqlParameterMetadata parameter : parameters) {
				if((result = parameter.validate(sqlParameter, alias)) != null) 
					return result;
			}
		}
		return null;
	}
}
