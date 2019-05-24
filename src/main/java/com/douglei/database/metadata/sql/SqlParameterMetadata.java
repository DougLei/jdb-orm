package com.douglei.database.metadata.sql;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.configuration.LocalConfigurationDialectHolder;
import com.douglei.database.dialect.datatype.DataTypeHandler;
import com.douglei.database.dialect.datatype.classtype.ClassDataTypeHandlerMapping;
import com.douglei.database.metadata.Metadata;
import com.douglei.database.metadata.MetadataType;
import com.douglei.instances.ognl.OgnlHandler;
import com.douglei.utils.StringUtil;
import com.douglei.utils.datatype.ValidationUtil;

/**
 * sql参数元数据
 * @author DougLei
 */
public class SqlParameterMetadata implements Metadata{
	private static final Logger logger = LoggerFactory.getLogger(SqlParameterMetadata.class);
	private String configurationText;
	
	/**
	 * 参数名
	 */
	private String name;
	/**
	 * 数据类型
	 */
	private DataTypeHandler dataTypeHandler;
	
	/**
	 * 是否使用占位符?
	 */
	private boolean usePlaceholder;
	/**
	 * 如果不使用占位符, 参数值的前缀
	 */
	private String placeholderPrefix;
	/**
	 * 如果不使用占位符, 参数值的后缀
	 */
	private String placeholderSuffix;
	
	public SqlParameterMetadata(String configurationText) {
		this.configurationText = configurationText;
		
		Map<String, String> propertyMap = resolvingPropertyMap(configurationText);
		setName(propertyMap.get("name"));
		setDataTypeHandler(propertyMap.get("datatypehandler"));
		setUsePlaceholder(propertyMap.get("useplaceholder"));
		setPlaceholderPrefix(propertyMap.get("placeholderprefix"));
		setPlaceholderSuffix(propertyMap.get("placeholdersuffix"));
	}
	
	// 解析出属性map集合
	private Map<String, String> resolvingPropertyMap(String configurationText) {
		String[] cts = configurationText.split(",");
		int length = cts.length;
		if(length < 1) {
			throw new MatchingSqlParameterException("sql参数, 必须配置参数名");
		}
		Map<String, String> propertyMap = new HashMap<String, String>(length);
		propertyMap.put("name", cts[0].trim());
		
		if(length > 1) {
			String[] keyValue = null;
			for(int i=1;i<length;i++) {
				keyValue = getKeyValue(cts[i]);
				if(keyValue != null) {
					propertyMap.put(keyValue[0], keyValue[1]);
				}
			}
		}
		return propertyMap;
	}
	private String[] getKeyValue(String confText) {
		if(confText != null) {
			confText = confText.trim();
			int equalIndex = confText.indexOf("=");
			if(equalIndex > 0 && equalIndex < (confText.length()-1)) {
				String[] keyValue = new String[2];
				keyValue[0] = confText.substring(0, equalIndex).trim().toLowerCase();
				keyValue[1] = confText.substring(equalIndex+1).trim();
				return keyValue;
			}
		}
		return null;
	}
	
	void setName(String name) {
		this.name = name;
	}
	void setDataTypeHandler(String dataType) {
		logger.debug("设置dataType配置值");
		if(StringUtil.isEmpty(dataType)) {
			this.dataTypeHandler = ClassDataTypeHandlerMapping.getDefaultDataTypeHandler();
		}else {
			this.dataTypeHandler = LocalConfigurationDialectHolder.getDialect().getDataTypeHandlerMapping().getDataTypeHandlerByCode(dataType);
		}
	}
	void setUsePlaceholder(String usePlaceholder) {
		logger.debug("设置usePlaceholder配置值");
		if(ValidationUtil.isBoolean(usePlaceholder)) {
			this.usePlaceholder = Boolean.parseBoolean(usePlaceholder);
		}else {
			this.usePlaceholder = true;
		}
	}
	void setPlaceholderPrefix(String placeholderPrefix) {
		logger.debug("设置placeholderPrefix配置值");
		if(StringUtil.isEmpty(placeholderPrefix)) {
			this.placeholderPrefix = "'";
		}else {
			this.placeholderPrefix = placeholderPrefix;
		}
	}
	void setPlaceholderSuffix(String placeholderSuffix) {
		logger.debug("设置placeholderSuffix配置值");
		if(StringUtil.isEmpty(placeholderSuffix)) {
			this.placeholderSuffix = "'";
		}else {
			this.placeholderSuffix = placeholderSuffix;
		}
	}
	
	
	public String getConfigurationText() {
		return configurationText;
	}
	public String getName() {
		return name;
	}
	public DataTypeHandler getDataTypeHandler() {
		return dataTypeHandler;
	}
	public boolean isUsePlaceholder() {
		return usePlaceholder;
	}
	public String getPlaceholderPrefix() {
		return placeholderPrefix;
	}
	public String getPlaceholderSuffix() {
		return placeholderSuffix;
	}

	@Override
	public String getCode() {
		return name;
	}
	
	@Override
	public MetadataType getMetadataType() {
		return MetadataType.SQL_PARAMETER;
	}

	@Override
	public String toString() {
		return "SqlParameterMetadata [name=" + name + ", dataTypeHandler=" + dataTypeHandler + ", usePlaceholder="
				+ usePlaceholder + ", placeholderPrefix=" + placeholderPrefix + ", placeholderSuffix="
				+ placeholderSuffix + "]";
	}
	
	private boolean unProcessNamePrefix = true;// 是否【没有】处理过name前缀, 默认都没有处理
	private boolean isSingleName;// 是否只是一个name, 没有.而需要ognl解析
	private void processNamePrefix(String sqlParameterNamePrefix) {
		if(unProcessNamePrefix) {
			unProcessNamePrefix = false;
			
			if(sqlParameterNamePrefix != null) {
				int subLength = sqlParameterNamePrefix.length()+1;// +1是把表达式后面的.去掉
				if(name.length() > subLength) {
					name = name.substring(subLength);
				}
			}
			isSingleName = name.indexOf(".") == -1;
		}
	}
	
	/**
	 * 获取值
	 * @param sqlParameter
	 * @param sqlParameterNamePrefix 即如果是alias.xxx, 要去除alias.
	 * @return
	 */
	public Object getValue(Object sqlParameter, String sqlParameterNamePrefix) {
		processNamePrefix(sqlParameterNamePrefix);
		
		Object value = null;
		if(sqlParameter instanceof Map<?, ?> && isSingleName) {
			value = ((Map<?, ?>)sqlParameter).get(name); 
		}else if(ValidationUtil.isBasicDataType(sqlParameter)){
			value = sqlParameter;
		}else {
			value = OgnlHandler.singleInstance().getObjectValue(name, sqlParameter);
		}
		
		// TODO 后续可以加入默认值
		return value;
	}
}
