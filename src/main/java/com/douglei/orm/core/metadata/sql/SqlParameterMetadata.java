package com.douglei.orm.core.metadata.sql;

import java.util.HashMap;
import java.util.Map;

import com.douglei.orm.context.EnvironmentContext;
import com.douglei.orm.context.xml.MappingXmlConfigContext;
import com.douglei.orm.core.dialect.datatype.DBDataType;
import com.douglei.orm.core.dialect.datatype.handler.AbstractDataTypeHandlerMapping;
import com.douglei.orm.core.dialect.datatype.handler.DataTypeHandler;
import com.douglei.orm.core.dialect.datatype.handler.classtype.ClassDataTypeHandler;
import com.douglei.orm.core.dialect.datatype.handler.dbtype.DBDataTypeFeatures;
import com.douglei.orm.core.dialect.datatype.handler.dbtype.DBDataTypeHandler;
import com.douglei.orm.core.metadata.Metadata;
import com.douglei.orm.core.metadata.MetadataType;
import com.douglei.orm.core.metadata.validator.DataValidateException;
import com.douglei.orm.core.metadata.validator.ValidatorHandler;
import com.douglei.orm.core.metadata.validator.ValidatorResult;
import com.douglei.orm.core.metadata.validator.internal._DataTypeValidator;
import com.douglei.tools.instances.ognl.OgnlHandler;
import com.douglei.tools.utils.StringUtil;
import com.douglei.tools.utils.datatype.VerifyTypeMatchUtil;
import com.douglei.tools.utils.datatype.converter.ConverterUtil;

/**
 * sql参数元数据
 * @author DougLei
 */
public class SqlParameterMetadata implements Metadata{
	
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
	
	private ValidatorHandler validatorHandler;// 验证器
	
	public SqlParameterMetadata(String configText) {
		this.configText = configText;
		
		Map<String, String> propertyMap = resolvingPropertyMap(configText);
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
		setNullable(propertyMap.get("nullable"));
		setDefaultValue(propertyMap.get("defaultValue"));
		setValidate(propertyMap.get("validate"));
		
		setValidatorHandler();
		propertyMap.clear();
	}
	
	// 解析出属性map集合
	private Map<String, String> resolvingPropertyMap(String configText) {
		String[] cts = configText.split(",");
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
		if(MappingXmlConfigContext.getContentType() != ContentType.PROCEDURE) {
			AbstractDataTypeHandlerMapping mapping = EnvironmentContext.getEnvironmentProperty().getDialect().getDataTypeHandlerMapping();
			if(StringUtil.isEmpty(dataType)) {
				this.dataType = mapping.getDefaultClassDataTypeHandler();
			}else {
				this.dataType = mapping.getDataTypeHandlerByCode(dataType);
			}
			this.dbDataType = ((DBDataTypeFeatures)this.dataType).getDBDataType();
		}
	}
	private void setDBDataType(String typeName) {
		if(MappingXmlConfigContext.getContentType() == ContentType.PROCEDURE) {
			AbstractDataTypeHandlerMapping mapping = EnvironmentContext.getEnvironmentProperty().getDialect().getDataTypeHandlerMapping();
			if(StringUtil.isEmpty(typeName)) {
				this.dataType = mapping.getDefaultDBDataTypeHandler();
			}else {
				this.dataType = mapping.getDBDataTypeHandlerByDBTypeName(typeName);
			}
			this.dbDataType = ((DBDataTypeFeatures)this.dataType).getDBDataType();
		}
	}
	private void setMode(String mode) {
		if(MappingXmlConfigContext.getContentType() == ContentType.PROCEDURE) {
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
		if(VerifyTypeMatchUtil.isBoolean(nullable)) {
			this.nullable = Boolean.parseBoolean(nullable);
		}
	}
	private void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	private void setValidate(String validate) {
		this.validate = Boolean.parseBoolean(validate);
	}
	
	private void setValidatorHandler() {
		ValidatorHandler validatorHandler = MappingXmlConfigContext.getSqlValidatorHandlerMap().get(name);
		if(validatorHandler == null && validate) {
			validatorHandler = new ValidatorHandler(name);
		}
		if(validatorHandler != null) {
			this.validate = true;
			this.validatorHandler = validatorHandler;
			if(validatorHandler.unexistsNullableValidator()) {
				this.validatorHandler.setNullableValidator(defaultValue==null?nullable:true);
				this.validatorHandler.addValidator(new _DataTypeValidator(dataType, length, precision));
			}
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
		}else if(ConverterUtil.isSimpleType(sqlParameter)){
			value = sqlParameter;
		}else {
			value = OgnlHandler.singleInstance().getObjectValue(name, sqlParameter);
		}
		
		if(value == null) {
			value = DefaultValueHandler.getDefaultValue(defaultValue);
		}
		doValidate(value);
		return value;
	}
	
	// 验证数据
	private void doValidate(Object value) {
		if(EnvironmentContext.getEnvironmentProperty().enableDataValidate() && validate) {
			ValidatorResult result = validatorHandler.doValidate(value);
			if(result != null) {
				throw new DataValidateException(descriptionName, name, value, result);
			}
		}
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
}
