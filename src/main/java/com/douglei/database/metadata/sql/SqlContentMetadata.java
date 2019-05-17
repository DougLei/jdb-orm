package com.douglei.database.metadata.sql;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.douglei.database.dialect.DialectType;
import com.douglei.database.metadata.Metadata;
import com.douglei.database.metadata.MetadataType;

/**
 * sql内容元数据
 * @author DougLei
 */
public class SqlContentMetadata implements Metadata{
	
	private String dialectTypeCode;
	private String content;
	
	// sql参数, 按照配置中定义的顺序记录
	private List<SqlParameterMetadata> sqlParameterOrders;
	
	public SqlContentMetadata(DialectType dialectType, String content) {
		this.dialectTypeCode = dialectType.getCode();
		this.content = content;
		resolvingParameters();
	}
	
	// 添加 sql parameter
	private void addSqlParameter(String configurationText) {
		if(sqlParameterOrders == null) {
			sqlParameterOrders = new ArrayList<SqlParameterMetadata>();
		}
		
		SqlParameterMetadata sqlParameter = new SqlParameterMetadata(configurationText);
		sqlParameterOrders.add(sqlParameter);
		replaceSqlContent(configurationText, sqlParameter);
	}
	
	// 替换Sql内容
	private void replaceSqlContent(String configurationText, SqlParameterMetadata sqlParameter) {
		if(sqlParameter.isUsePlaceholder()) {
			content = content.replaceAll("${"+configurationText+"$}", "?");
		}else{
			content = content.replaceAll(configurationText, sqlParameter.getName());
		}
	}

	/**
	 * 从content中解析出parameter集合
	 */
	private void resolvingParameters() {
		Matcher matcher = pattern.matcher(content);
		int startIndex, endIndex;
		while(matcher.find()) {
			startIndex = matcher.start();
			if(matcher.find()) {
				endIndex = matcher.start();
				addSqlParameter(content.substring(startIndex+2, endIndex-1));
			}else {
				throw new MatchingSqlParameterException("sql content中, 配置的参数异常, [$]标识符不匹配(多一个/少一个), 请检查");
			}
		}
	}
	private static final Pattern pattern = Pattern.compile("[\\$]", Pattern.MULTILINE);// 匹配$
	
	public List<SqlParameterMetadata> getSqlParameterOrders() {
		return sqlParameterOrders;
	}

	/**
	 * 即dialectType的code, 用来区分不同dialect, 调用不同的sql语句
	 */
	@Override
	public String getCode() {
		return dialectTypeCode;
	}
	
	@Override
	public MetadataType getMetadataType() {
		return MetadataType.SQL_CONTENT;
	}
}
