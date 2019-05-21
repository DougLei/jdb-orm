package com.douglei.database.metadata.sql.content.node.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.douglei.database.metadata.MetadataType;
import com.douglei.database.metadata.sql.MatchingSqlParameterException;
import com.douglei.database.metadata.sql.SqlParameterMetadata;
import com.douglei.database.metadata.sql.content.node.NodeMetadata;

/**
 * 
 * @author DougLei
 */
public abstract class AbstractNodeMetadata implements NodeMetadata{
	
	private String content;
	private int placeholderCount;
	
	// sql参数, 按照配置中定义的顺序记录
	private List<SqlParameterMetadata> sqlParameterOrders;
	private static final Pattern prefixPattern = Pattern.compile("(\\$\\{)", Pattern.MULTILINE);// 匹配${
	private static final Pattern suffixPattern = Pattern.compile("[\\}]", Pattern.MULTILINE);// 匹配}
	
	public AbstractNodeMetadata(String content) {
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
				throw new MatchingSqlParameterException("sql content中, 配置的参数异常, [$]标识符不匹配(多一个/少一个), 请检查");
			}
		}
		
		if(sqlParameterOrders != null) {
			for (SqlParameterMetadata sqlParameter : sqlParameterOrders) {
				replaceSqlParameterInSqlContent(sqlParameter);
			}
		}
	}
	
	// 添加 sql parameter
	private void addSqlParameter(String configurationText) {
		if(sqlParameterOrders == null) {
			sqlParameterOrders = new ArrayList<SqlParameterMetadata>();
		}
		sqlParameterOrders.add(new SqlParameterMetadata(configurationText));
	}
	
	// 替换Sql语句内容中的参数
	private void replaceSqlParameterInSqlContent(SqlParameterMetadata sqlParameter) {
		if(sqlParameter.isUsePlaceholder()) {
			placeholderCount++;
			content = content.replaceAll("\\$\\{"+sqlParameter.getConfigurationText()+"\\}\\$", "?");
		}else{
			content = content.replaceAll(sqlParameter.getConfigurationText(), sqlParameter.getName());
		}
	}
	
	public String getContent() {
		return content;
	}
	public int getPlaceholderCount() {
		return placeholderCount;
	}
	public List<SqlParameterMetadata> getSqlParameterOrders() {
		return sqlParameterOrders;
	}

	@Deprecated
	@Override
	public String getCode() {
		return null;
	}

	@Deprecated
	@Override
	public MetadataType getMetadataType() {
		return null;
	}
}
