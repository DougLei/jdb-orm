package com.douglei.database.metadata.sql;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.database.dialect.DialectType;
import com.douglei.database.metadata.Metadata;
import com.douglei.database.metadata.MetadataType;
import com.douglei.database.metadata.sql.content.SqlContentType;

/**
 * sql内容元数据
 * @author DougLei
 */
public class SqlContentMetadata implements Metadata{
	private static final Logger logger = LoggerFactory.getLogger(SqlContentMetadata.class);
	
	private String dialectTypeCode;
	private SqlContentType type;
	private String content;
	private int placeholderCount;
	
	// sql参数, 按照配置中定义的顺序记录
	private List<SqlParameterMetadata> sqlParameterOrders;
	
	public SqlContentMetadata(DialectType dialectType, SqlContentType type, String content) {
		this.dialectTypeCode = dialectType.getCode();
		this.type = type;
		this.content = content;
		resolvingParameters();
		if(logger.isDebugEnabled()) {
			logger.debug("解析出的sql content= {}", this.content);
			logger.debug("解析出的sql parameters= {}", sqlParameterOrders==null?"无参数":sqlParameterOrders.toString());
		}
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
	private static final Pattern prefixPattern = Pattern.compile("(\\$\\{)", Pattern.MULTILINE);// 匹配${
	private static final Pattern suffixPattern = Pattern.compile("[\\}]", Pattern.MULTILINE);// 匹配}
	
	public static void main(String[] args) throws Exception{
		Element element = new SAXReader().read(new File("D:\\softwares\\developments\\workspaces\\jdb-orm\\src\\test\\resources\\mappings\\sql\\sql.smp.xml")).getRootElement().element("sql")
				.element("content");
		
		List<?> els = element.elements();
		for (Object object : els) {
			System.out.println(((Element)object).asXML());
		}
		
		String content = element.getStringValue();
		System.out.println(content);
		
		Matcher perfixMatcher = prefixPattern.matcher(content);
		Matcher suffixMatcher = suffixPattern.matcher(content);
		int startIndex, endIndex;
		while(perfixMatcher.find()) {
			startIndex = perfixMatcher.start();
			if(suffixMatcher.find()) {
				endIndex = suffixMatcher.start();
				System.out.println(content.substring(startIndex+2, endIndex));
			}else {
				throw new MatchingSqlParameterException("sql content中, 配置的参数异常, [$]标识符不匹配(多一个/少一个), 请检查");
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
	
	
	public SqlContentType getType() {
		return type;
	}
	public String getContent() {
		return content;
	}
	public List<SqlParameterMetadata> getSqlParameterOrders() {
		return sqlParameterOrders;
	}
	public int getPlaceholderCount() {
		return placeholderCount;
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
