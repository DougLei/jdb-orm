package com.douglei.core.metadata.sql;

import java.util.HashMap;
import java.util.Map;

import com.douglei.context.DBRunEnvironmentContext;
import com.douglei.context.RunMappingConfigurationContext;
import com.douglei.core.dialect.datatype.handler.AbstractDataTypeHandlerMapping;
import com.douglei.core.dialect.datatype.handler.DataTypeHandler;
import com.douglei.core.dialect.datatype.handler.classtype.AbstractStringDataTypeHandler;
import com.douglei.core.dialect.datatype.handler.classtype.ClassDataTypeHandler;
import com.douglei.core.dialect.datatype.handler.dbtype.DBDataTypeHandler;
import com.douglei.core.metadata.Metadata;
import com.douglei.core.metadata.MetadataType;
import com.douglei.instances.ognl.OgnlHandler;
import com.douglei.utils.StringUtil;
import com.douglei.utils.datatype.ValidationUtil;

/**
 * sql参数元数据
 * @author DougLei
 */
public class SqlParameterMetadata implements Metadata{
	protected String configurationText;
	protected Map<String, String> propertyMap; // 根据configurationText解析出来的<属性:值>map集合
	
	protected String name;// 参数名
	protected DataTypeHandler dataType;// 数据类型
	
	protected boolean usePlaceholder;// 是否使用占位符?
	protected String placeholderPrefix;// 如果不使用占位符, 参数值的前缀
	protected String placeholderSuffix;// 如果不使用占位符, 参数值的后缀
	
	protected SqlParameterMode mode;// 输入输出类型
	
	public SqlParameterMetadata(String configurationText) {
		this(configurationText, true);
	}
	protected SqlParameterMetadata(String configurationText, boolean clearPropertyMap) {
		this.configurationText = configurationText;
		
		propertyMap = resolvingPropertyMap(configurationText);
		setName(propertyMap.get("name"));
		setDataType(propertyMap.get("datatype"));
		setUsePlaceholder(propertyMap.get("useplaceholder"));
		setPlaceholderPrefix(propertyMap.get("placeholderprefix"));
		setPlaceholderSuffix(propertyMap.get("placeholdersuffix"));
		setDBDataType(propertyMap.get("dbType"));
		setMode(propertyMap.get("mode"));
		clearPropertyMap(clearPropertyMap);
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
	void setDataType(String dataType) {
		if(RunMappingConfigurationContext.getCurrentSqlContentType() != SqlContentType.PROCEDURE) {
			AbstractDataTypeHandlerMapping mapping = DBRunEnvironmentContext.getDialect().getDataTypeHandlerMapping();
			if(StringUtil.isEmpty(dataType)) {
				this.dataType = mapping.getDefaultClassDataTypeHandler();
			}else {
				this.dataType = mapping.getDataTypeHandlerByCode(dataType);
			}
		}
	}
	void setUsePlaceholder(String usePlaceholder) {
		if(ValidationUtil.isBoolean(usePlaceholder)) {
			this.usePlaceholder = Boolean.parseBoolean(usePlaceholder);
		}else {
			this.usePlaceholder = true;
		}
	}
	void setPlaceholderPrefix(String placeholderPrefix) {
		if(StringUtil.isEmpty(placeholderPrefix)) {
			if(dataType instanceof AbstractStringDataTypeHandler) {
				this.placeholderPrefix = "'";
			}else {
				this.placeholderPrefix = "";
			}
		}else {
			this.placeholderPrefix = placeholderPrefix;
		}
	}
	void setPlaceholderSuffix(String placeholderSuffix) {
		if(StringUtil.isEmpty(placeholderSuffix)) {
			if(dataType instanceof AbstractStringDataTypeHandler) {
				this.placeholderSuffix = "'";
			}else {
				this.placeholderSuffix = "";
			}
		}else {
			this.placeholderSuffix = placeholderSuffix;
		}
	}
	void setDBDataType(String typeName) {
		if(RunMappingConfigurationContext.getCurrentSqlContentType() == SqlContentType.PROCEDURE) {
			AbstractDataTypeHandlerMapping mapping = DBRunEnvironmentContext.getDialect().getDataTypeHandlerMapping();
			if(StringUtil.isEmpty(typeName)) {
				this.dataType = mapping.getDefaultDBDataTypeHandler();
			}else {
				this.dataType = mapping.getDBDataTypeHandlerByDBTypeName(typeName);
			}
		}
	}
	void setMode(String mode) {
		if(RunMappingConfigurationContext.getCurrentSqlContentType() == SqlContentType.PROCEDURE) {
			if(StringUtil.notEmpty(mode)) {
				this.mode = SqlParameterMode.toValue(mode);
			}
			if(this.mode == null) {
				this.mode = SqlParameterMode.IN;
			}
		}
	}
	
	// 情况
	protected void clearPropertyMap(boolean clearPropertyMap) {
		if(clearPropertyMap) {
			propertyMap.clear();
			propertyMap = null;
		}
	}

	public String getConfigurationText() {
		return configurationText;
	}
	public String getName() {
		return name;
	}
	public ClassDataTypeHandler getDataType() {
		return (ClassDataTypeHandler) dataType;
	}
	public DBDataTypeHandler getDBDataType() {
		return (DBDataTypeHandler) dataType;
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
	public SqlParameterMode getMode() {
		return mode;
	}

	@Deprecated
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
		return "SqlParameterMetadata [name=" + name + ", dataType=" + dataType.toString() + ", usePlaceholder="
				+ usePlaceholder + ", placeholderPrefix=" + placeholderPrefix + ", placeholderSuffix="
				+ placeholderSuffix + "]";
	}
	
	private boolean unProcessNamePrefix = true;// 是否【没有】处理过name前缀, 默认都没有处理
	private boolean isSingleName;// 是否只是一个name, 如果不是的话(xxx.xxx), 则需要ognl解析
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
	 * @return
	 */
	public Object getValue(Object sqlParameter) {
		return getValue(sqlParameter, null);
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
		return value;
	}
}
