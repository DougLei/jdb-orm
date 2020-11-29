package com.douglei.orm.mapping.impl.sql.metadata.content.node.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import com.douglei.orm.configuration.EnvironmentContext;
import com.douglei.orm.mapping.impl.sql.metadata.content.node.ExecuteSqlNode;
import com.douglei.orm.mapping.impl.sql.metadata.content.node.SqlNode;
import com.douglei.orm.mapping.impl.sql.metadata.parameter.SqlParameterConfigHolder;
import com.douglei.orm.mapping.impl.sql.metadata.parameter.SqlParameterMetadata;
import com.douglei.orm.mapping.impl.sql.metadata.parser.content.node.SqlNodeType;
import com.douglei.orm.mapping.metadata.validator.ValidationResult;
import com.douglei.orm.sessionfactory.sessions.session.sql.PurposeEntity;
import com.douglei.tools.utils.StringUtil;

/**
 * 
 * @author DougLei
 */
public class TextSqlNode implements SqlNode {
	private static final long serialVersionUID = 4155598587310382398L;
	
	private String content;
	private List<SqlParameterMetadata> sqlParameters;// sql参数, 按照配置中定义的顺序记录
	
	public TextSqlNode(String content) {
		this.content = content.trim();
		resolvingParameters();
	}
	// 从content中解析出parameter集合
	private void resolvingParameters() {
		if(StringUtil.isEmpty(content)) 
			return;
		
		SqlParameterConfigHolder sqlParameterConfigHolder = EnvironmentContext.getProperty().getSqlParameterConfigHolder();
		Matcher perfixMatcher = sqlParameterConfigHolder.getPrefixPattern().matcher(content);
		if(sqlParameterConfigHolder.getPsRelation() == SqlParameterConfigHolder.SAME) {
			int startIndex;
			while(perfixMatcher.find()) {
				startIndex = perfixMatcher.start();
				if(perfixMatcher.find()) {
					addSqlParameter(content.substring(startIndex + sqlParameterConfigHolder.getPrefixLength(), perfixMatcher.start()), sqlParameterConfigHolder);
				}else {
					throw new IllegalArgumentException("content=["+content+"], 参数配置异常, ["+sqlParameterConfigHolder.getPrefix()+"]标识符不匹配(少一个), 请检查");
				}
			}
			if(perfixMatcher.find())
				throw new IllegalArgumentException("content=["+content+"], 参数配置异常, ["+sqlParameterConfigHolder.getPrefix()+"]标识符不匹配(多一个), 请检查");
		}else {
			Matcher suffixMatcher = sqlParameterConfigHolder.getSuffixPattern().matcher(content);
			if(sqlParameterConfigHolder.getPsRelation() == SqlParameterConfigHolder.DIFFERENT){
				while(perfixMatcher.find()) {
					if(suffixMatcher.find()) {
						addSqlParameter(content.substring(perfixMatcher.start() + sqlParameterConfigHolder.getPrefixLength(), suffixMatcher.start()), sqlParameterConfigHolder);
					}else {
						throw new IllegalArgumentException("content=["+content+"], 参数配置异常, ["+sqlParameterConfigHolder.getSuffix()+"]标识符不匹配(至少少一个), 请检查");
					}
				}
				if(suffixMatcher.find())
					throw new IllegalArgumentException("content=["+content+"], 参数配置异常, ["+sqlParameterConfigHolder.getPrefix()+"和"+sqlParameterConfigHolder.getSuffix()+"]标识符不匹配(至少多一个或少一个), 请检查");
			}else if(sqlParameterConfigHolder.getPsRelation() == SqlParameterConfigHolder.SUFFIX_IN_PREFIX){
				while(perfixMatcher.find()) {
					if(suffixMatcher.find() && suffixMatcher.find()) {
						addSqlParameter(content.substring(perfixMatcher.start() + sqlParameterConfigHolder.getPrefixLength(), suffixMatcher.start()), sqlParameterConfigHolder);
					}else {
						throw new IllegalArgumentException("content=["+content+"], 参数配置异常, ["+sqlParameterConfigHolder.getSuffix()+"]标识符不匹配(至少少一个), 请检查");
					}
				}
				if(suffixMatcher.find())
					throw new IllegalArgumentException("content=["+content+"], 参数配置异常, ["+sqlParameterConfigHolder.getPrefix()+"和"+sqlParameterConfigHolder.getSuffix()+"]标识符不匹配(至少多一个或少一个), 请检查");
			}else if(sqlParameterConfigHolder.getPsRelation() == SqlParameterConfigHolder.PREFIX_IN_SUFFIX){
				if(perfixMatcher.find()) {
					do {
						if(suffixMatcher.find()) {
							addSqlParameter(content.substring(perfixMatcher.start() + sqlParameterConfigHolder.getPrefixLength(), suffixMatcher.start()), sqlParameterConfigHolder);
						}else {
							throw new IllegalArgumentException("content=["+content+"], 参数配置异常, ["+sqlParameterConfigHolder.getSuffix()+"]标识符不匹配(至少少一个), 请检查");
						}
					} while(perfixMatcher.find() && perfixMatcher.find());
					if(suffixMatcher.find())
						throw new IllegalArgumentException("content=["+content+"], 参数配置异常, ["+sqlParameterConfigHolder.getPrefix()+"和"+sqlParameterConfigHolder.getSuffix()+"]标识符不匹配(至少多一个或少一个), 请检查");
				}
			}
		}
		
		if(sqlParameters != null) {
			for (SqlParameterMetadata sqlParameter : sqlParameters) {
				if(sqlParameter.isUsePlaceholder()) {
					content = content.replaceAll(sqlParameterConfigHolder.getPrefix() + sqlParameter.getConfigText() + sqlParameterConfigHolder.getSuffix(), "?");
				}else{
					content = content.replaceAll(sqlParameter.getConfigText(), sqlParameter.getName());
				}
			}
		}
	}
	// 添加 sql parameter
	private void addSqlParameter(String configText, SqlParameterConfigHolder sqlParameterConfigHolder) {
		if(sqlParameters == null) 
			sqlParameters = new ArrayList<SqlParameterMetadata>();
		sqlParameters.add(new SqlParameterMetadata(configText, sqlParameterConfigHolder));
	}
	
	@Override
	public SqlNodeType getType() {
		return SqlNodeType.TEXT;
	}
	
	@Override
	public ExecuteSqlNode getExecuteSqlNode(PurposeEntity purposeEntity, Object sqlParameter, String alias) {
		return new ExecuteSqlNode(purposeEntity, content, sqlParameters, sqlParameter, alias);
	}

	@Override
	public ValidationResult validateParameter(Object sqlParameter, String alias) {
		if(sqlParameters != null) {
			ValidationResult result = null;
			for (SqlParameterMetadata parameter : sqlParameters) {
				if((result = parameter.validate(sqlParameter, alias)) != null) 
					return result;
			}
		}
		return null;
	}
	
	/**
	 * 获取sql内容
	 * @return
	 */
	public String getContent() {
		return content;
	}
	
	/**
	 * 获取sql参数, 按照sql内容中定义的顺序(可重复)
	 * @return
	 */
	public List<SqlParameterMetadata> getSqlParameters(){
		return sqlParameters;
	}
}
