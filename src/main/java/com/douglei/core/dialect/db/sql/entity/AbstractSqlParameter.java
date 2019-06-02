package com.douglei.core.dialect.db.sql.entity;

import java.util.HashMap;
import java.util.Map;

import com.douglei.context.DBRunEnvironmentContext;
import com.douglei.context.RunMappingConfigurationContext;
import com.douglei.core.dialect.datatype.handler.AbstractDataTypeHandlerMapping;
import com.douglei.core.dialect.datatype.handler.DataTypeHandler;
import com.douglei.core.dialect.datatype.handler.classtype.AbstractStringDataTypeHandler;
import com.douglei.core.dialect.datatype.handler.classtype.ClassDataTypeHandler;
import com.douglei.core.dialect.datatype.handler.dbtype.DBDataTypeHandler;
import com.douglei.core.metadata.sql.MatchingSqlParameterException;
import com.douglei.core.metadata.sql.SqlContentType;
import com.douglei.core.metadata.sql.SqlParameterMode;
import com.douglei.utils.StringUtil;
import com.douglei.utils.datatype.ValidationUtil;

/**
 * 
 * @author DougLei
 */
public abstract class AbstractSqlParameter {
	protected String configurationText;
	protected Map<String, String> propertyMap; // 根据configurationText解析出来的<属性:值>map集合
	
	protected String name;// 参数名
	protected DataTypeHandler dataType;// 数据类型
	
	protected SqlParameterMode mode;// 输入输出类型
	
	protected boolean usePlaceholder;// 是否使用占位符?
	protected String placeholderPrefix;// 如果不使用占位符, 参数值的前缀
	protected String placeholderSuffix;// 如果不使用占位符, 参数值的后缀
	
	protected AbstractSqlParameter(String configurationText, boolean clearPropertyMap) {
		this.configurationText = configurationText;
		
		propertyMap = resolvingPropertyMap(configurationText);
		setName(propertyMap.get("name"));
		setDataType(propertyMap.get("datatype"));
		setDBDataType(propertyMap.get("dbType"));
		setMode(propertyMap.get("mode"));
		
		setUsePlaceholder(propertyMap.get("useplaceholder"));
		if(!this.usePlaceholder) {
			setPlaceholderPrefix(propertyMap.get("placeholderprefix"));
			setPlaceholderSuffix(propertyMap.get("placeholdersuffix"));
		}
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
	void setUsePlaceholder(String usePlaceholder) {
		if(ValidationUtil.isBoolean(usePlaceholder)) {
			this.usePlaceholder = Boolean.parseBoolean(usePlaceholder);
		}else {
			this.usePlaceholder = true;
		}
	}
	void setPlaceholderPrefix(String placeholderPrefix) {
		if(StringUtil.isEmpty(placeholderPrefix)) {
			if(dataType instanceof AbstractStringDataTypeHandler || (dataType instanceof DBDataTypeHandler && ((DBDataTypeHandler)dataType).isCharacterType())) {
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
			if(dataType instanceof AbstractStringDataTypeHandler || (dataType instanceof DBDataTypeHandler && ((DBDataTypeHandler)dataType).isCharacterType())) {
				this.placeholderSuffix = "'";
			}else {
				this.placeholderSuffix = "";
			}
		}else {
			this.placeholderSuffix = placeholderSuffix;
		}
	}
	// 清空属性map
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
}
