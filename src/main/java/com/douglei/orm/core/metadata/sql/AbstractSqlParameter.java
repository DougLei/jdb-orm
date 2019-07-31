package com.douglei.orm.core.metadata.sql;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.douglei.orm.context.DBRunEnvironmentContext;
import com.douglei.orm.context.RunMappingConfigurationContext;
import com.douglei.orm.core.dialect.datatype.DBDataType;
import com.douglei.orm.core.dialect.datatype.handler.AbstractDataTypeHandlerMapping;
import com.douglei.orm.core.dialect.datatype.handler.DataTypeHandler;
import com.douglei.orm.core.dialect.datatype.handler.classtype.ClassDataTypeHandler;
import com.douglei.orm.core.dialect.datatype.handler.dbtype.DBDataTypeHandler;
import com.douglei.orm.core.dialect.datatype.handler.dbtype.DBDataTypeFeatures;
import com.douglei.tools.utils.StringUtil;
import com.douglei.tools.utils.datatype.VerifyTypeMatchUtil;

/**
 * 
 * @author DougLei
 */
public abstract class AbstractSqlParameter implements Serializable{
	private static final long serialVersionUID = -7947921907711909432L;
	
	protected String configurationText;
	protected Map<String, String> propertyMap; // 根据configurationText解析出来的<属性:值>map集合
	
	protected String name;// 参数名
	protected String descriptionName;// 描述名
	protected DataTypeHandler dataType;// 数据类型
	private DBDataType dbDataType;// 数据库的数据类型, 根据dataTypeHandler得到
	
	protected SqlParameterMode mode;// 输入输出类型
	
	protected boolean usePlaceholder;// 是否使用占位符?
	protected String valuePrefix;// 如果不使用占位符, 参数值的前缀
	protected String valueSuffix;// 如果不使用占位符, 参数值的后缀
	
	protected short length;// 长度
	protected short precision;// 精度
	protected boolean nullabled;// 是否可为空
	protected String defaultValue;// 默认值
	protected boolean validate;// 是否验证
	
	protected AbstractSqlParameter(String configurationText) {
		this.configurationText = configurationText;
		
		propertyMap = resolvingPropertyMap(configurationText);
		setName(propertyMap.get("name"));
		setDescriptionName(propertyMap.get("descriptionName"));
		setDataType(propertyMap.get("datatype"));
		setDBDataType(propertyMap.get("dbType"));
		setMode(propertyMap.get("mode"));
		
		setUsePlaceholder(propertyMap.get("useplaceholder"));
		if(!this.usePlaceholder) {
			setValuePrefix(propertyMap.get("valuePrefix"));
			setValueSuffix(propertyMap.get("valueSuffix"));
		}
		setLength(propertyMap.get("length"));
		setPrecision(propertyMap.get("precision"));
		setNullabled(propertyMap.get("nullabled"));
		setDefaultValue(propertyMap.get("defaultValue"));
		setValidate(propertyMap.get("validate"));
		
		propertyMap.clear();
		propertyMap = null;
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
	
	private void setName(String name) {
		this.name = name;
	}
	private void setDescriptionName(String descriptionName) {
		if(StringUtil.isEmpty(descriptionName)) {
			descriptionName = name;
		}
		this.descriptionName = descriptionName;
	}
	private void setDataType(String dataType) {
		if(RunMappingConfigurationContext.getCurrentSqlContentType() != SqlContentType.PROCEDURE) {
			AbstractDataTypeHandlerMapping mapping = DBRunEnvironmentContext.getEnvironmentProperty().getDialect().getDataTypeHandlerMapping();
			if(StringUtil.isEmpty(dataType)) {
				this.dataType = mapping.getDefaultClassDataTypeHandler();
			}else {
				this.dataType = mapping.getDataTypeHandlerByCode(dataType);
			}
			this.dbDataType = ((DBDataTypeFeatures)this.dataType).getDBDataType();
		}
	}
	private void setDBDataType(String typeName) {
		if(RunMappingConfigurationContext.getCurrentSqlContentType() == SqlContentType.PROCEDURE) {
			AbstractDataTypeHandlerMapping mapping = DBRunEnvironmentContext.getEnvironmentProperty().getDialect().getDataTypeHandlerMapping();
			if(StringUtil.isEmpty(typeName)) {
				this.dataType = mapping.getDefaultDBDataTypeHandler();
			}else {
				this.dataType = mapping.getDBDataTypeHandlerByDBTypeName(typeName);
			}
			this.dbDataType = ((DBDataTypeFeatures)this.dataType).getDBDataType();
		}
	}
	private void setMode(String mode) {
		if(RunMappingConfigurationContext.getCurrentSqlContentType() == SqlContentType.PROCEDURE) {
			if(StringUtil.notEmpty(mode)) {
				this.mode = SqlParameterMode.toValue(mode);
			}
			if(this.mode == null) {
				this.mode = SqlParameterMode.IN;
			}
		}
	}
	private void setUsePlaceholder(String usePlaceholder) {
		if(VerifyTypeMatchUtil.isBoolean(usePlaceholder)) {
			this.usePlaceholder = Boolean.parseBoolean(usePlaceholder);
		}else {
			this.usePlaceholder = true;
		}
	}
	private void setValuePrefix(String valuePrefix) {
		if(StringUtil.isEmpty(valuePrefix)) {
			if(((DBDataTypeFeatures)dataType).isCharacterType()) {
				this.valuePrefix = "'";
			}else {
				this.valuePrefix = "";
			}
		}else {
			this.valuePrefix = valuePrefix;
		}
	}
	private void setValueSuffix(String valueSuffix) {
		if(StringUtil.isEmpty(valueSuffix)) {
			if(((DBDataTypeFeatures)dataType).isCharacterType()) {
				this.valueSuffix = "'";
			}else {
				this.valueSuffix = "";
			}
		}else {
			this.valueSuffix = valueSuffix;
		}
	}
	private void setLength(String length) {
		if(VerifyTypeMatchUtil.isLimitShort(length)) {
			this.length = Short.parseShort(length);
		}
		this.length = this.dbDataType.correctInputLength(this.length);
	}
	private void setPrecision(String precision) {
		if(VerifyTypeMatchUtil.isLimitShort(precision)) {
			this.precision = Short.parseShort(precision);
		}
		this.precision = this.dbDataType.correctInputPrecision(this.length, this.precision);
	}
	private void setNullabled(String nullabled) {
		if(VerifyTypeMatchUtil.isBoolean(nullabled)) {
			this.nullabled = Boolean.parseBoolean(nullabled);
		}
	}
	private void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	private void setValidate(String validate) {
		if(DBRunEnvironmentContext.getEnvironmentProperty().getEnableDataValidation() && VerifyTypeMatchUtil.isBoolean(validate)) {
			this.validate = Boolean.parseBoolean(validate);
		}
	}

	public String getConfigurationText() {
		return configurationText;
	}
	public String getName() {
		return name;
	}
	public String getDescriptionName() {
		return descriptionName;
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
	public String getValuePrefix() {
		return valuePrefix;
	}
	public String getValueSuffix() {
		return valueSuffix;
	}
	public SqlParameterMode getMode() {
		return mode;
	}
	public short getLength() {
		return length;
	}
	public short getPrecision() {
		return precision;
	}
	public boolean isNullabled() {
		return nullabled;
	}
	public String getDefaultValue() {
		return defaultValue;
	}
	public boolean isValidate() {
		return validate;
	}
}