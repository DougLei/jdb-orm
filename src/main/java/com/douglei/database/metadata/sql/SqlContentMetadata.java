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
	
	private DialectType dialect;
	private String content;
	
	// sql参数名的顺序, 按照配置中${}$的顺序记录, 同一个参数可能会使用多次, 所以这里按照顺都记录下来
	private List<String> sqlParameterNameOrders;
	// sql参数, 这里只记录参数, 和配置中的顺序无关, 同一个名称的参数, 只记录一次
	private List<SqlParameterMetadata> sqlParameters;
	
	public SqlContentMetadata(DialectType dialect, String content) {
		this.dialect = dialect;
		this.content = content;
		resolvingParameters();
	}
	
	// 初始化参数集合
	private void initParameterCollection() {
		if(sqlParameterNameOrders == null) {
			sqlParameterNameOrders = new ArrayList<String>();
		}
		if(sqlParameters == null) {
			sqlParameters = new ArrayList<SqlParameterMetadata>();
		}
	}
	
	// 添加 sql parameter
	private void addSqlParameter(SqlParameterMetadata sqlParameter) {
		String sqlParameterName = sqlParameter.getName();
		if(!sqlParameterNameOrders.contains(sqlParameterName)) {
			sqlParameters.add(sqlParameter);
		}
		sqlParameterNameOrders.add(sqlParameterName);
	}

	/**
	 * 从content中解析出parameter集合
	 */
	private void resolvingParameters() {
		Matcher matcher = pattern.matcher(content);
		int startIndex, endIndex;
		while(matcher.find()) {
			initParameterCollection();
			startIndex = matcher.start();
			if(matcher.find()) {
				endIndex = matcher.start();
				addSqlParameter(new SqlParameterMetadata(content.substring(startIndex+2, endIndex-1)));
			}else {
				throw new MatchingSqlParameterException("sql content中, 配置的参数异常, [$]标识符不匹配(多一个/少一个), 请检查");
			}
		}
	}
	private static final Pattern pattern = Pattern.compile("[\\$]", Pattern.MULTILINE);// 匹配$
	
	@Override
	public String getCode() {
		return dialect.getCode();
	}
	
	@Override
	public MetadataType getMetadataType() {
		return MetadataType.SQL_CONTENT;
	}
}
