package com.douglei.orm.core.metadata.sql.content.node.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import com.douglei.orm.core.metadata.sql.MatchingSqlParameterException;
import com.douglei.orm.core.metadata.sql.SqlParameterDeclareConfiguration;
import com.douglei.orm.core.metadata.sql.SqlParameterMetadata;
import com.douglei.orm.core.metadata.sql.content.node.ExecuteSqlNode;
import com.douglei.orm.core.metadata.sql.content.node.SqlNode;
import com.douglei.orm.core.metadata.validator.ValidationResult;
import com.douglei.tools.utils.StringUtil;

/**
 * 
 * @author DougLei
 */
public abstract class AbstractSqlNode implements SqlNode{
	private static final long serialVersionUID = -1464862023103747959L;
	
	protected String content;
	protected List<SqlParameterMetadata> sqlParameterByDefinedOrders;// sql参数, 按照配置中定义的顺序记录
	
	public AbstractSqlNode(String content) {
		this.content = content.trim();
		resolvingParameters();
	}
	
	/**
	 * 从content中解析出parameter集合
	 */
	private void resolvingParameters() {
		if(StringUtil.isEmpty(content)) {
			return;
		}
		
		int startIndex, endIndex;
		Matcher perfixMatcher = SqlParameterDeclareConfiguration.prefixPattern.matcher(content);
		if(SqlParameterDeclareConfiguration.prefixPattern == SqlParameterDeclareConfiguration.suffixPattern) {
			// 如果前后缀一样, 则只用前缀去匹配, 获取每个sql参数
			while(perfixMatcher.find()) {
				startIndex = perfixMatcher.start();
				if(perfixMatcher.find()) {
					endIndex = perfixMatcher.start();
					addSqlParameter(content.substring(startIndex+SqlParameterDeclareConfiguration.prefixLength, endIndex));
				}else {
					throw new MatchingSqlParameterException("content=["+content+"], 参数配置异常, ["+SqlParameterDeclareConfiguration.prefix+"]标识符不匹配(多一个/少一个), 请检查");
				}
			}
		}else {
			// 如果前后缀不一致, 则要分别去匹配, 获取每个sql参数
			Matcher suffixMatcher = SqlParameterDeclareConfiguration.suffixPattern.matcher(content);
			while(perfixMatcher.find()) {
				startIndex = perfixMatcher.start();
				if(suffixMatcher.find()) {
					endIndex = suffixMatcher.start();
					addSqlParameter(content.substring(startIndex+SqlParameterDeclareConfiguration.prefixLength, endIndex));
				}else {
					throw new MatchingSqlParameterException("content=["+content+"], 参数配置异常, ["+SqlParameterDeclareConfiguration.prefix+"和"+SqlParameterDeclareConfiguration.suffix+"]标识符不匹配(多一个/少一个), 请检查");
				}
			}
		}
		
		if(sqlParameterByDefinedOrders != null) {
			for (SqlParameterMetadata sqlParameter : sqlParameterByDefinedOrders) {
				replaceSqlParameterInSqlContent(sqlParameter);
			}
		}
	}
	
	// 添加 sql parameter
	private void addSqlParameter(String configText) {
		if(sqlParameterByDefinedOrders == null) {
			sqlParameterByDefinedOrders = new ArrayList<SqlParameterMetadata>();
		}
		sqlParameterByDefinedOrders.add(new SqlParameterMetadata(configText));
	}
	
	// 替换Sql语句内容中的参数
	private void replaceSqlParameterInSqlContent(SqlParameterMetadata sqlParameter) {
		if(sqlParameter.isUsePlaceholder()) {
			content = content.replaceAll(SqlParameterDeclareConfiguration.prefixPatternString+sqlParameter.getConfigText()+SqlParameterDeclareConfiguration.suffixPatternString, "?");
		}else{
			content = content.replaceAll(sqlParameter.getConfigText(), sqlParameter.getName());
		}
	}
	
	@Override
	public ExecuteSqlNode getExecuteSqlNode(Object sqlParameter, String sqlParameterNamePrefix) {
		return new ExecuteSqlNode(content, sqlParameterByDefinedOrders, sqlParameter, sqlParameterNamePrefix);
	}

	@Override
	public ValidationResult validateParameter(Object sqlParameter, String sqlParameterNamePrefix) {
		if(sqlParameterByDefinedOrders != null) {
			ValidationResult result = null;
			for (SqlParameterMetadata parameter : sqlParameterByDefinedOrders) {
				if((result = parameter.doValidate(sqlParameter, sqlParameterNamePrefix)) != null) {
					return result;
				}
			}
		}
		return null;
	}
}
