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
import com.douglei.orm.mapping.impl.sql.metadata.parser.content.node.SqlNodeParserException;
import com.douglei.orm.mapping.impl.sql.metadata.parser.content.node.SqlNodeType;
import com.douglei.orm.sessionfactory.sessions.session.sql.PurposeEntity;

/**
 * 
 * @author DougLei
 */
public class TextSqlNode implements SqlNode {
	
	private String content;
	private List<SqlParameterMetadata> parameters;// sql参数, 按照配置中定义的顺序记录
	
	public TextSqlNode(String content) {
		this.content = content.trim();
		resolvingParameters();
	}
	// 从content中解析出parameter集合
	private void resolvingParameters() {
		SqlMappingConfiguration config = EnvironmentContext.getEnvironment().getMappingHandler().getMappingConfiguration().getSqlMappingConfiguration();
		Matcher perfixMatcher = config.getParameterPrefixPattern().matcher(content);
		if(config.getRelationship() == SqlMappingParameterPSRelation.SAME) {
			int startIndex;
			while(perfixMatcher.find()) {
				startIndex = perfixMatcher.start();
				if(perfixMatcher.find()) {
					addSqlParameter(content.substring(startIndex + config.getParameterPrefixLength(), perfixMatcher.start()), config.getParameterSplit());
				}else {
					throw new SqlNodeParserException("content=["+content+"], 参数配置异常, ["+config.getParameterPrefix()+"]标识符不匹配(少一个), 请检查");
				}
			}
			if(perfixMatcher.find())
				throw new SqlNodeParserException("content=["+content+"], 参数配置异常, ["+config.getParameterPrefix()+"]标识符不匹配(多一个), 请检查");
		}else {
			Matcher suffixMatcher = config.getParameterSuffixPattern().matcher(content);
			if(config.getRelationship() == SqlMappingParameterPSRelation.DIFFERENT){
				while(perfixMatcher.find()) {
					if(suffixMatcher.find()) {
						addSqlParameter(content.substring(perfixMatcher.start() + config.getParameterPrefixLength(), suffixMatcher.start()), config.getParameterSplit());
					}else {
						throw new SqlNodeParserException("content=["+content+"], 参数配置异常, ["+config.getParameterSuffix()+"]标识符不匹配(至少少一个), 请检查");
					}
				}
				if(suffixMatcher.find())
					throw new SqlNodeParserException("content=["+content+"], 参数配置异常, ["+config.getParameterPrefix()+"和"+config.getParameterSuffix()+"]标识符不匹配(至少多一个或少一个), 请检查");
			}else if(config.getRelationship() == SqlMappingParameterPSRelation.SUFFIX_IN_PREFIX){
				while(perfixMatcher.find()) {
					if(suffixMatcher.find() && suffixMatcher.find()) {
						addSqlParameter(content.substring(perfixMatcher.start() + config.getParameterPrefixLength(), suffixMatcher.start()), config.getParameterSplit());
					}else {
						throw new SqlNodeParserException("content=["+content+"], 参数配置异常, ["+config.getParameterSuffix()+"]标识符不匹配(至少少一个), 请检查");
					}
				}
				if(suffixMatcher.find())
					throw new SqlNodeParserException("content=["+content+"], 参数配置异常, ["+config.getParameterPrefix()+"和"+config.getParameterSuffix()+"]标识符不匹配(至少多一个或少一个), 请检查");
			}else if(config.getRelationship() == SqlMappingParameterPSRelation.PREFIX_IN_SUFFIX){
				if(perfixMatcher.find()) {
					do {
						if(suffixMatcher.find()) {
							addSqlParameter(content.substring(perfixMatcher.start() + config.getParameterPrefixLength(), suffixMatcher.start()), config.getParameterSplit());
						}else {
							throw new SqlNodeParserException("content=["+content+"], 参数配置异常, ["+config.getParameterSuffix()+"]标识符不匹配(至少少一个), 请检查");
						}
					} while(perfixMatcher.find() && perfixMatcher.find());
					if(suffixMatcher.find())
						throw new SqlNodeParserException("content=["+content+"], 参数配置异常, ["+config.getParameterPrefix()+"和"+config.getParameterSuffix()+"]标识符不匹配(至少多一个或少一个), 请检查");
				}
			}
		}
		
		if(parameters != null) {
			for (SqlParameterMetadata parameter : parameters) {
				if(parameter.isPlaceholder()) {
					content = content.replaceAll(config.getParameterPrefix() + parameter.getConfigText() + config.getParameterSuffix(), "?");
				}else{
					content = content.replaceAll(parameter.getConfigText(), parameter.getName());
				}
			}
		}
	}
	// 添加 sql parameter
	private void addSqlParameter(String configText, String split) {
		if(parameters == null) 
			parameters = new ArrayList<SqlParameterMetadata>();
		parameters.add(new SqlParameterMetadata(configText, split));
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
