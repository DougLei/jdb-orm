package com.douglei.orm.mapping.impl.sql.parser.parameter;

import java.util.HashMap;
import java.util.Map;

import com.douglei.orm.dialect.datatype.DataTypeClassification;
import com.douglei.orm.dialect.datatype.db.DBDataTypeEntity;
import com.douglei.orm.dialect.datatype.db.DBDataTypeUtil;
import com.douglei.orm.mapping.MappingParseToolContext;
import com.douglei.orm.mapping.impl.sql.metadata.content.ContentType;
import com.douglei.orm.mapping.impl.sql.metadata.parameter.Mode;
import com.douglei.orm.mapping.impl.sql.metadata.parameter.SqlParameterMetadata;
import com.douglei.orm.mapping.metadata.MetadataParseException;
import com.douglei.tools.RegularExpressionUtil;
import com.douglei.tools.StringUtil;

/**
 * SqlParameterMetadata解析器
 * @author DougLei
 */
public class SqlParameterMetadataParser {

	/**
	 * 解析SqlParameterMetadata
	 * @param configText
	 * @param split
	 * @return
	 * @throws MetadataParseException
	 */
	public SqlParameterMetadata parse(String configText, String split) throws MetadataParseException{
		// 设置配置的内容, 如果存在正则表达式的关键字, 则增加\转义
		SqlParameterMetadata metadata = new SqlParameterMetadata(RegularExpressionUtil.includeKey(configText)?RegularExpressionUtil.addBackslash4Key(configText):configText);
		
		// 解析参数配置的属性
		Map<String, String> propertyMap = parsePropertyMap(configText, split);
		
		// 设置name
		metadata.setName(propertyMap.get("name"));
		
		// 设置数据类型
		setDBDataType(metadata, propertyMap);
		
		// 设置默认值; 是否可为空
		metadata.setDefaultValue(propertyMap.get("defaultvalue"));
		metadata.setNullable(!"false".equalsIgnoreCase(propertyMap.get("nullable")));
		
		// 设置占位符
		setPlaceholder(metadata, propertyMap);
		
		propertyMap.clear();
		return metadata;
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
	private void setDBDataType(SqlParameterMetadata metadata, Map<String, String> propertyMap) {
		String typeName = propertyMap.get("dbtype");
		DataTypeClassification classification = DataTypeClassification.DB;
		
		if(MappingParseToolContext.getMappingParseTool().getCurrentSqlContentType() == ContentType.PROCEDURE) {
			if(StringUtil.isEmpty(typeName))
				throw new NullPointerException("存储过程中, 参数["+metadata.getName()+"]的dbType不能为空");
			
			if(propertyMap.get("mode") == null) {
				metadata.setMode(Mode.IN);
			}else {
				metadata.setMode(Mode.valueOf(propertyMap.get("mode").toUpperCase()));
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
	private void setPlaceholder(SqlParameterMetadata metadata, Map<String, String> propertyMap) {
		metadata.setPlaceholder(!"false".equalsIgnoreCase(propertyMap.get("placeholder")));
		if(!metadata.isPlaceholder()) {
			setPrefix(metadata, propertyMap.get("prefix"));
			setSuffix(metadata, propertyMap.get("suffix"));
		}
	}
	private void setPrefix(SqlParameterMetadata metadata, String prefix) {
		if(StringUtil.isEmpty(prefix)) {
			metadata.setPrefix(metadata.getDBDataType().isCharacterType()?"'":"");
		}else {
			metadata.setPrefix(prefix);
		}
	}
	private void setSuffix(SqlParameterMetadata metadata, String suffix) {
		if(StringUtil.isEmpty(suffix)) {
			metadata.setSuffix(metadata.getDBDataType().isCharacterType()?"'":"");
		}else {
			metadata.setSuffix(suffix);
		}
	}
}
