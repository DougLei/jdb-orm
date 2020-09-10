package com.douglei.orm.core.metadata.sql;

import java.util.HashMap;
import java.util.Map;

import com.douglei.orm.configuration.EnvironmentContext;
import com.douglei.orm.configuration.impl.element.environment.mapping.MappingResolverContext;
import com.douglei.orm.core.dialect.datatype.DBDataType;
import com.douglei.orm.core.dialect.datatype.handler.AbstractDataTypeHandlerMapping;
import com.douglei.orm.core.dialect.datatype.handler.DataTypeHandler;
import com.douglei.orm.core.dialect.datatype.handler.classtype.ClassDataTypeHandler;
import com.douglei.orm.core.dialect.datatype.handler.dbtype.DBDataTypeFeatures;
import com.douglei.orm.core.dialect.datatype.handler.dbtype.DBDataTypeHandler;
import com.douglei.orm.core.metadata.Metadata;
import com.douglei.orm.core.metadata.MetadataType;
import com.douglei.orm.core.metadata.validator.DataValidationException;
import com.douglei.orm.core.metadata.validator.ValidateHandler;
import com.douglei.orm.core.metadata.validator.ValidationResult;
import com.douglei.orm.core.metadata.validator.internal._DataTypeValidator;
import com.douglei.tools.instances.ognl.OgnlHandler;
import com.douglei.tools.utils.RegularExpressionUtil;
import com.douglei.tools.utils.StringUtil;
import com.douglei.tools.utils.datatype.VerifyTypeMatchUtil;
import com.douglei.tools.utils.datatype.converter.ConverterUtil;
import com.douglei.tools.utils.reflect.IntrospectorUtil;

/**
 * sql参数元数据
 * @author DougLei
 */
public class SqlParameterMetadata implements Metadata{
	private static final long serialVersionUID = 3107760956690978471L;

	private String configText;
	
	private String name;// 参数名
	private String descriptionName;// 描述名
	private DataTypeHandler dataType;// 数据类型
	private DBDataType dbDataType;// 数据库的数据类型, 根据dataTypeHandler得到
	
	private SqlParameterMode mode;// 输入输出类型
	
	private boolean usePlaceholder;// 是否使用占位符?
	private String valuePrefix;// 如果不使用占位符, 参数值的前缀
	private String valueSuffix;// 如果不使用占位符, 参数值的后缀
	
	private short length;// 长度
	private short precision;// 精度
	private boolean nullable;// 是否可为空
	private String defaultValue;// 默认值
	private boolean validate;// 是否验证
	
	private ValidateHandler validateHandler;// 验证器
	
	private SqlParameterConfigHolder configHolder;
	
	public SqlParameterMetadata(String configText, SqlParameterConfigHolder sqlParameterConfigHolder) {
		// 设置配置的内容, 如果存在正则表达式的关键字, 则增加\转义
		this.configText = sqlParameterConfigHolder.splitIncludeRegExKey()?RegularExpressionUtil.transferRegularExpressionKey(configText):configText;

		Map<String, String> propertyMap = resolvingPropertyMap(configText, sqlParameterConfigHolder);
		setName(propertyMap.get("name"));
		setDescriptionName(propertyMap.get("descriptionname"));
		setDataType(propertyMap.get("datatype"));
		setDBDataType(propertyMap.get("dbType"));
		setMode(propertyMap.get("mode"));
		
		setUsePlaceholder(propertyMap.get("useplaceholder"));
		if(!this.usePlaceholder) {
			setValuePrefix(propertyMap.get("valueprefix"));
			setValueSuffix(propertyMap.get("valuesuffix"));
		}
		setLength(propertyMap.get("length"));
		setPrecision(propertyMap.get("precision"));
		setNullable(propertyMap.get("nullable"));
		setDefaultValue(propertyMap.get("defaultvalue"));
		setValidate(propertyMap.get("validate"));
		
		setValidateHandler();
		propertyMap.clear();
		this.configHolder = sqlParameterConfigHolder;
	}
	
	// 解析出属性map集合
	private Map<String, String> resolvingPropertyMap(String configText, SqlParameterConfigHolder sqlParameterConfigHolder) {
		String[] cts = configText.split(sqlParameterConfigHolder.getSplit());
		int length = cts.length;
		if(length < 1) {
			throw new MatchingSqlParameterException("sql参数, 必须配置参数名");
		}
		Map<String, String> propertyMap = new HashMap<String, String>();
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
		if(MappingResolverContext.getCurrentSqlType() != ContentType.PROCEDURE) {
			AbstractDataTypeHandlerMapping mapping = EnvironmentContext.getDialect().getDataTypeHandlerMapping();
			if(StringUtil.isEmpty(dataType)) {
				this.dataType = mapping.getDefaultClassDataTypeHandler();
			}else {
				this.dataType = mapping.getDataTypeHandlerByCode(dataType);
			}
			this.dbDataType = ((DBDataTypeFeatures)this.dataType).getDBDataType();
		}
	}
	private void setDBDataType(String typeName) {
		if(MappingResolverContext.getCurrentSqlType() == ContentType.PROCEDURE) {
			AbstractDataTypeHandlerMapping mapping = EnvironmentContext.getDialect().getDataTypeHandlerMapping();
			if(StringUtil.isEmpty(typeName)) {
				this.dataType = mapping.getDefaultDBDataTypeHandler();
			}else {
				this.dataType = mapping.getDBDataTypeHandlerByDBTypeName(typeName);
			}
			this.dbDataType = ((DBDataTypeFeatures)this.dataType).getDBDataType();
		}
	}
	private void setMode(String mode) {
		if(MappingResolverContext.getCurrentSqlType() == ContentType.PROCEDURE) {
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
	private void setNullable(String nullable) {
		this.nullable = Boolean.parseBoolean(nullable);
	}
	private void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	private void setValidate(String validate) {
		this.validate = Boolean.parseBoolean(validate);
	}
	
	private void setValidateHandler() {
		ValidateHandler validateHandler = MappingResolverContext.getSqlValidateHandlers().get(name);
		if(validateHandler == null && validate) {
			validateHandler = new ValidateHandler(name);
		}
		if(validateHandler != null) {
			this.validate = true;
			this.validateHandler = validateHandler;
			this.validateHandler.setNullableValidator(defaultValue==null?nullable:true);
			this.validateHandler.addValidator(new _DataTypeValidator(dataType, length, precision));
		}
	}

	@Deprecated
	@Override
	public String getCode() {
		return name;
	}
	
	private boolean unProcessNamePrefix = true;// 是否【没有】处理过name前缀, 默认都没有处理
	private boolean isSingleName;// 是否只是一个name, 如果不是的话(即alias.xxx这种多层级name), 则需要ognl解析
	private void processNamePrefix(String sqlParameterNamePrefix) {
		if(unProcessNamePrefix) {
			unProcessNamePrefix = false;
			
			// 在foreach中, 传入一个List<String>, alias=id, #{id}, 即别名和参数名一致, 这个时候就不能substring
			if(sqlParameterNamePrefix != null) {
				int subLength = sqlParameterNamePrefix.length()+1;// +1是把表达式后面的.去掉
				if(name.length() > subLength) {
					name = name.substring(subLength);
				}
			}
			isSingleName = name.indexOf(".") == -1;// 可能会出现alias.xx.xx的多层级形式
		}
	}
	
	// 获取值
	private Object getValue_(Object sqlParameter, String sqlParameterNamePrefix) {
		processNamePrefix(sqlParameterNamePrefix);
		
		Object value = null;
		if(sqlParameter instanceof Map<?, ?> && isSingleName) {
			value = ((Map<?, ?>)sqlParameter).get(name); 
		}else if(ConverterUtil.isSimpleType(sqlParameter)){
			value = sqlParameter;
		}else {
			value = OgnlHandler.getSingleton().getObjectValue(name, sqlParameter);
		}
		
		if(value == null) {
			value = configHolder.getDefaultValueHandler().getDefaultValue(defaultValue);
			if(value != null) {
				if(isSingleName) {
					IntrospectorUtil.setProperyValue(sqlParameter, name, value);
				}else {
					int dot = name.lastIndexOf(".");
					IntrospectorUtil.setProperyValue(OgnlHandler.getSingleton().getObjectValue(name.substring(0, dot), sqlParameter), this.name.substring(dot+1), value);
				}
			}
		}
		return value;
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
		Object value = getValue_(sqlParameter, sqlParameterNamePrefix);
		validate(value);
		return value;
	}
	
	// 验证数据
	private void validate(Object value) {
		if(EnvironmentContext.getEnvironmentProperty().enableDataValidate() && validate) {
			ValidationResult result = validateHandler.validate(value);
			if(result != null) {
				throw new DataValidationException(descriptionName, name, value, result);
			}
		}
	}
	
	/**
	 * 验证数据
	 * @param sqlParameter
	 * @param sqlParameterNamePrefix
	 * @return
	 */
	public ValidationResult validate(Object sqlParameter, String sqlParameterNamePrefix) {
		if(validate) {
			return validateHandler.validate(getValue_(sqlParameter, sqlParameterNamePrefix));
		}
		return null;
	}
	
	@Override
	public MetadataType getMetadataType() {
		return MetadataType.SQL_PARAMETER;
	}
	
	public String getConfigText() {
		return configText;
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
	public boolean isNullable() {
		return nullable;
	}
	public String getDefaultValue() {
		return defaultValue;
	}
	public boolean isValidate() {
		return validate;
	}
	public SqlParameterConfigHolder getConfigHolder() {
		return configHolder;
	}
	
	@Override
	public String toString() {
		return "SqlParameterMetadata [name=" + name + "]";
	}
}
