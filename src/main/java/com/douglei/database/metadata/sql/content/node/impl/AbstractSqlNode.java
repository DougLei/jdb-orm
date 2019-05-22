package com.douglei.database.metadata.sql.content.node.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.douglei.database.metadata.sql.MatchingSqlParameterException;
import com.douglei.database.metadata.sql.SqlParameterMetadata;
import com.douglei.database.metadata.sql.content.node.ExecuteSqlNode;
import com.douglei.database.metadata.sql.content.node.SqlNode;

/**
 * 
 * @author DougLei
 */
public abstract class AbstractSqlNode implements SqlNode{
	
	private String content;
	
	// sql参数, 按照配置中定义的顺序记录
	private List<SqlParameterMetadata> sqlParameterByDefinedOrders;
	private static final Pattern prefixPattern = Pattern.compile("(\\$\\{)", Pattern.MULTILINE);// 匹配${
	private static final Pattern suffixPattern = Pattern.compile("[\\}]", Pattern.MULTILINE);// 匹配}
	
	public AbstractSqlNode(String content) {
		this.content = content;
		resolvingParameters();
	}
	
	/**
	 * 从content中解析出parameter集合
	 */
	private void resolvingParameters() {
		Matcher perfixMatcher = prefixPattern.matcher(content);
		Matcher suffixMatcher = suffixPattern.matcher(content);
		int startIndex, endIndex;
		while(perfixMatcher.find()) {
			startIndex = perfixMatcher.start();
			if(suffixMatcher.find()) {
				endIndex = suffixMatcher.start();
				addSqlParameter(content.substring(startIndex+2, endIndex));
			}else {
				throw new MatchingSqlParameterException("content=["+content+"], 参数配置异常, [${ 和 }]标识符不匹配(多一个/少一个), 请检查");
			}
		}
		
		if(sqlParameterByDefinedOrders != null) {
			for (SqlParameterMetadata sqlParameter : sqlParameterByDefinedOrders) {
				replaceSqlParameterInSqlContent(sqlParameter);
			}
		}
	}
	
	// 添加 sql parameter
	private void addSqlParameter(String configurationText) {
		if(sqlParameterByDefinedOrders == null) {
			sqlParameterByDefinedOrders = new ArrayList<SqlParameterMetadata>();
		}
		sqlParameterByDefinedOrders.add(new SqlParameterMetadata(configurationText));
	}
	
	// 替换Sql语句内容中的参数
	private void replaceSqlParameterInSqlContent(SqlParameterMetadata sqlParameter) {
		if(sqlParameter.isUsePlaceholder()) {
			content = content.replaceAll("\\$\\{"+sqlParameter.getConfigurationText()+"\\}", "?");
		}else{
			content = content.replaceAll(sqlParameter.getConfigurationText(), sqlParameter.getName());
		}
	}

	@Override
	public ExecuteSqlNode getExecuteSqlNode(Map<String, Object> sqlParameterMap) {
		return new ExecuteSqlNode(content, sqlParameterByDefinedOrders, sqlParameterMap);
	}
}
