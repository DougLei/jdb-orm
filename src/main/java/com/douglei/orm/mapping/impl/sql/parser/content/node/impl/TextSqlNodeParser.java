package com.douglei.orm.mapping.impl.sql.parser.content.node.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

import org.w3c.dom.Node;

import com.douglei.orm.configuration.environment.EnvironmentContext;
import com.douglei.orm.configuration.environment.mapping.SqlMappingConfiguration;
import com.douglei.orm.configuration.environment.mapping.SqlMappingParameterPSRelation;
import com.douglei.orm.dialect.datatype.DataTypeClassification;
import com.douglei.orm.dialect.datatype.db.DBDataTypeEntity;
import com.douglei.orm.dialect.datatype.db.DBDataTypeUtil;
import com.douglei.orm.mapping.MappingParseToolContext;
import com.douglei.orm.mapping.impl.sql.SqlNodeType;
import com.douglei.orm.mapping.impl.sql.metadata.content.ContentType;
import com.douglei.orm.mapping.impl.sql.metadata.content.node.SqlNode;
import com.douglei.orm.mapping.impl.sql.metadata.content.node.impl.ParameterNode;
import com.douglei.orm.mapping.impl.sql.metadata.content.node.impl.ParameterMode;
import com.douglei.orm.mapping.impl.sql.metadata.content.node.impl.TextSqlNode;
import com.douglei.orm.mapping.impl.sql.parser.content.node.SqlNodeParser;
import com.douglei.orm.mapping.impl.sql.parser.content.node.SqlNodeParseException;
import com.douglei.orm.mapping.metadata.MetadataParseException;
import com.douglei.tools.RegularExpressionUtil;
import com.douglei.tools.StringUtil;

/**
 * 
 * @author DougLei
 */
public class TextSqlNodeParser extends SqlNodeParser {
	private static ParameterNodeParser parameterNodeParser = new ParameterNodeParser();
	
	@Override
	public SqlNode parse(Node node) throws SqlNodeParseException {
		String sql = node.getNodeValue();
		if(StringUtil.isEmpty(sql)) 
			return null;
		
		TextSqlNodeEntity entity = new TextSqlNodeEntity(sql);
		return new TextSqlNode(entity.getSql(), entity.getParameters());
	}
	
	@Override
	public SqlNodeType getType() {
		return SqlNodeType.TEXT;
	}
	
	/**
	 * 
	 * @author DougLei
	 */
	private class TextSqlNodeEntity {
		private String sql;
		private List<ParameterNode> parameters;
		
		public TextSqlNodeEntity(String sql) {
			this.sql = sql.trim();
			parseParameters();
		}

		// 从content中解析出parameter集合
		private void parseParameters() {
			SqlMappingConfiguration config = EnvironmentContext.getEnvironment().getMappingHandler().getMappingConfiguration().getSqlMappingConfiguration();
			Matcher perfixMatcher = config.getParameterPrefixPattern().matcher(sql);
			if(config.getRelationship() == SqlMappingParameterPSRelation.SAME) {
				int startIndex;
				while(perfixMatcher.find()) {
					startIndex = perfixMatcher.start();
					if(perfixMatcher.find()) {
						addSqlParameter(sql.substring(startIndex + config.getParameterPrefixLength(), perfixMatcher.start()), config.getParameterSplit());
					}else {
						throw new SqlNodeParseException("sql=["+sql+"], 参数配置异常, ["+config.getParameterPrefix()+"]标识符不匹配(少一个), 请检查");
					}
				}
				if(perfixMatcher.find())
					throw new SqlNodeParseException("sql=["+sql+"], 参数配置异常, ["+config.getParameterPrefix()+"]标识符不匹配(多一个), 请检查");
			}else {
				Matcher suffixMatcher = config.getParameterSuffixPattern().matcher(sql);
				if(config.getRelationship() == SqlMappingParameterPSRelation.DIFFERENT){
					while(perfixMatcher.find()) {
						if(suffixMatcher.find()) {
							addSqlParameter(sql.substring(perfixMatcher.start() + config.getParameterPrefixLength(), suffixMatcher.start()), config.getParameterSplit());
						}else {
							throw new SqlNodeParseException("sql=["+sql+"], 参数配置异常, ["+config.getParameterSuffix()+"]标识符不匹配(至少少一个), 请检查");
						}
					}
					if(suffixMatcher.find())
						throw new SqlNodeParseException("sql=["+sql+"], 参数配置异常, ["+config.getParameterPrefix()+"和"+config.getParameterSuffix()+"]标识符不匹配(至少多一个或少一个), 请检查");
				}else if(config.getRelationship() == SqlMappingParameterPSRelation.SUFFIX_IN_PREFIX){
					while(perfixMatcher.find()) {
						if(suffixMatcher.find() && suffixMatcher.find()) {
							addSqlParameter(sql.substring(perfixMatcher.start() + config.getParameterPrefixLength(), suffixMatcher.start()), config.getParameterSplit());
						}else {
							throw new SqlNodeParseException("sql=["+sql+"], 参数配置异常, ["+config.getParameterSuffix()+"]标识符不匹配(至少少一个), 请检查");
						}
					}
					if(suffixMatcher.find())
						throw new SqlNodeParseException("sql=["+sql+"], 参数配置异常, ["+config.getParameterPrefix()+"和"+config.getParameterSuffix()+"]标识符不匹配(至少多一个或少一个), 请检查");
				}else if(config.getRelationship() == SqlMappingParameterPSRelation.PREFIX_IN_SUFFIX){
					if(perfixMatcher.find()) {
						do {
							if(suffixMatcher.find()) {
								addSqlParameter(sql.substring(perfixMatcher.start() + config.getParameterPrefixLength(), suffixMatcher.start()), config.getParameterSplit());
							}else {
								throw new SqlNodeParseException("sql=["+sql+"], 参数配置异常, ["+config.getParameterSuffix()+"]标识符不匹配(至少少一个), 请检查");
							}
						} while(perfixMatcher.find() && perfixMatcher.find());
						if(suffixMatcher.find())
							throw new SqlNodeParseException("sql=["+sql+"], 参数配置异常, ["+config.getParameterPrefix()+"和"+config.getParameterSuffix()+"]标识符不匹配(至少多一个或少一个), 请检查");
					}
				}
			}
			
			if(parameters != null) {
				for (ParameterNode parameter : parameters) {
					if(parameter.isPlaceholder()) {
						sql = sql.replaceAll(config.getParameterPrefix() + parameter.getConfigText() + config.getParameterSuffix(), "?");
					}else{
						sql = sql.replaceAll(parameter.getConfigText(), parameter.getName());
					}
				}
			}
		}
		// 添加 sql parameter
		private void addSqlParameter(String configText, String split) {
			if(parameters == null) 
				parameters = new ArrayList<ParameterNode>();
			parameters.add(parameterNodeParser.parse(configText, split));
		}
		
		public String getSql() {
			return sql;
		}
		public List<ParameterNode> getParameters() {
			return parameters;
		} 
	}
}

/**
 * SqlParameterMetadata解析器
 * @author DougLei
 */
class ParameterNodeParser {

	public ParameterNode parse(String configText, String split) throws MetadataParseException{
		// 设置配置的内容, 如果存在正则表达式的关键字, 则增加\转义
		ParameterNode sql = new ParameterNode(RegularExpressionUtil.includeKey(configText)?RegularExpressionUtil.addBackslash4Key(configText):configText);
		
		// 解析参数配置的属性
		Map<String, String> propertyMap = parsePropertyMap(configText, split);
		
		// 设置name
		sql.setName(propertyMap.get("name"));
		
		// 设置数据类型
		setDBDataType(sql, propertyMap);
		
		// 设置默认值; 是否可为空
		sql.setDefaultValue(propertyMap.get("defaultvalue"));
		sql.setNullable(!"false".equalsIgnoreCase(propertyMap.get("nullable")));
		
		// 设置占位符
		setPlaceholder(sql, propertyMap);
		
		propertyMap.clear();
		return sql;
	}
	
	// 解析参数属性map集合
	private Map<String, String> parsePropertyMap(String configText, String split) {
		String[] cts = configText.split(split);
		int length = cts.length;
		if(length == 0) 
			throw new NullPointerException("sql参数, 必须配置参数名");
		
		Map<String, String> propertyMap = new HashMap<String, String>();
		String name = cts[0].trim(); // 这里解析出参数名
		if(StringUtil.isEmpty(name))
			throw new NullPointerException("sql参数, 必须配置参数名");
		propertyMap.put("name", name);
		
		if(length > 1) {
			String[] keyValue = null;
			for(int i=1;i<length;i++) {
				keyValue = getKeyValue(cts[i]);
				if(keyValue != null) 
					propertyMap.put(keyValue[0], keyValue[1]);
			}
		}
		return propertyMap;
	}
	private String[] getKeyValue(String str) {
		if(str.length() > 0) {
			str = str.trim();
			int equalIndex = str.indexOf("=");
			if(equalIndex > 0 && equalIndex < (str.length()-1)) {
				String[] keyValue = new String[2];
				keyValue[0] = str.substring(0, equalIndex).trim().toLowerCase();
				keyValue[1] = str.substring(equalIndex+1).trim();
				return keyValue;
			}
		}
		return null;
	}
	
	/**
	 * 设置数据类型
	 * @param metadata
	 * @param propertyMap
	 */
	private void setDBDataType(ParameterNode metadata, Map<String, String> propertyMap) {
		String typeName = propertyMap.get("dbtype");
		DataTypeClassification classification = DataTypeClassification.DB;
		
		if(MappingParseToolContext.getMappingParseTool().getCurrentSqlContentType() == ContentType.PROCEDURE) {
			if(StringUtil.isEmpty(typeName))
				throw new NullPointerException("存储过程中, 参数["+metadata.getName()+"]的dbType不能为空");
			
			if(propertyMap.get("mode") == null) {
				metadata.setMode(ParameterMode.IN);
			}else {
				metadata.setMode(ParameterMode.valueOf(propertyMap.get("mode").toUpperCase()));
			}
		} else {
			if(StringUtil.isEmpty(typeName)) {
				typeName = propertyMap.get("datatype");
				if(StringUtil.isEmpty(typeName))
					typeName = "string";
				classification = DataTypeClassification.MAPPING;
			}
		}
		
		// 这里不对dataType进行转大/小写的操作, 原因是, dataType可能是一个自定义类的全路径, 转换后无法进行反射构建实例
		DBDataTypeEntity dataTypeEntity = DBDataTypeUtil.get(classification, typeName, propertyMap.get("length"), propertyMap.get("precision"));
		metadata.setDBDataType(dataTypeEntity.getDBDataType());
		metadata.setLength(dataTypeEntity.getLength());
		metadata.setPrecision(dataTypeEntity.getPrecision());
	}
	
	/**
	 * 设置占位符
	 * @param metadata
	 * @param propertyMap
	 */
	private void setPlaceholder(ParameterNode metadata, Map<String, String> propertyMap) {
		metadata.setPlaceholder(!"false".equalsIgnoreCase(propertyMap.get("placeholder")));
		if(!metadata.isPlaceholder()) {
			setPrefix(metadata, propertyMap.get("prefix"));
			setSuffix(metadata, propertyMap.get("suffix"));
		}
	}
	private void setPrefix(ParameterNode metadata, String prefix) {
		if(StringUtil.isEmpty(prefix)) {
			metadata.setPrefix(metadata.getDBDataType().isCharacterType()?"'":"");
		}else {
			metadata.setPrefix(prefix);
		}
	}
	private void setSuffix(ParameterNode metadata, String suffix) {
		if(StringUtil.isEmpty(suffix)) {
			metadata.setSuffix(metadata.getDBDataType().isCharacterType()?"'":"");
		}else {
			metadata.setSuffix(suffix);
		}
	}
}