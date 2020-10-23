package com.douglei.orm.mapping.impl.sql.metadata.parameter;

import java.util.HashMap;
import java.util.Map;

import com.douglei.orm.dialect.datatype.db.DBDataType;
import com.douglei.orm.dialect.datatype.db.util.DBDataTypeUtil;
import com.douglei.orm.dialect.datatype.db.util.DBDataTypeWrapper;
import com.douglei.orm.mapping.impl.MappingParserContext;
import com.douglei.orm.mapping.impl.sql.metadata.content.ContentType;
import com.douglei.orm.mapping.metadata.Metadata;
import com.douglei.orm.mapping.metadata.validator.ValidateHandler;
import com.douglei.orm.mapping.metadata.validator.ValidationResult;
import com.douglei.orm.mapping.metadata.validator.impl._DataTypeValidator;
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

	private String configText;
	
	private String name;// 参数名
	private DBDataType dbDataType;// 数据类型
	private int length;// 长度
	private int precision;// 精度
	
	private SqlParameterMode mode;// 输入输出类型
	
	private boolean usePlaceholder;// 是否使用占位符?
	private String valuePrefix;// 如果不使用占位符, 参数值的前缀
	private String valueSuffix;// 如果不使用占位符, 参数值的后缀
	
	private boolean nullable;// 是否可为空
	private String defaultValue;// 默认值
	private boolean validate;// 是否验证
	private String description;// 描述
	
	private ValidateHandler validateHandler;// 验证器
	
	private SqlParameterConfigHolder configHolder;
	
	public SqlParameterMetadata(String configText, SqlParameterConfigHolder sqlParameterConfigHolder) {
		// 设置配置的内容, 如果存在正则表达式的关键字, 则增加\转义
		this.configText = sqlParameterConfigHolder.splitIncludeRegExKey()?RegularExpressionUtil.transferRegularExpressionKey(configText):configText;

		Map<String, String> propertyMap = resolvingPropertyMap(configText, sqlParameterConfigHolder);
		setDataType(propertyMap);
		
		setUsePlaceholder(propertyMap);
		setNullable(propertyMap.get("nullable"));
		setDefaultValue(propertyMap.get("defaultvalue"));
		setValidate(propertyMap.get("validate"));
		setDescription(propertyMap.get("description"));
		
		setValidateHandler();
		propertyMap.clear();
		this.configHolder = sqlParameterConfigHolder;
	}
	
	// 解析出属性map集合
	private Map<String, String> resolvingPropertyMap(String configText, SqlParameterConfigHolder sqlParameterConfigHolder) {
		String[] cts = configText.split(sqlParameterConfigHolder.getSplit());
		int length = cts.length;
		if(length == 0) 
			throw new NullPointerException("sql参数, 必须配置参数名");
		
		Map<String, String> propertyMap = new HashMap<String, String>();
		this.name = cts[0].trim(); // 这里设置参数名
		if(StringUtil.isEmpty(this.name))
			throw new NullPointerException("sql参数, 必须配置参数名");
		
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
	
	private void setDataType(Map<String, String> propertyMap) {
		String dataType;
		if(MappingParserContext.getCurrentSqlType() == ContentType.PROCEDURE) {
			dataType = propertyMap.get("dbtype");
			if(StringUtil.isEmpty(dataType))
				throw new NullPointerException("存储过程中, 参数["+name+"]的dbType不能为空");
			
			this.mode = SqlParameterMode.toValue(propertyMap.get("mode"));
		} else {
			dataType = propertyMap.get("datatype");
			if(StringUtil.isEmpty(dataType))
				dataType = "string";
		}
		
		// 这里不对dataType进行转大/小写的操作, 原因是, dataType可能是一个自定义类的全路径, 转换后无法进行反射构建实例
		DBDataTypeWrapper wrapper = DBDataTypeUtil.get(propertyMap.get("length"), propertyMap.get("precision"), dataType);
		this.dbDataType = wrapper.getDBDataType();
		this.length = wrapper.getLength();
		this.precision = wrapper.getPrecision();
	}
	private void setUsePlaceholder(Map<String, String> propertyMap) {
		String value = propertyMap.get("useplaceholder");
		if(VerifyTypeMatchUtil.isBoolean(value)) {
			this.usePlaceholder = Boolean.parseBoolean(value);
		}else {
			this.usePlaceholder = true;
		}
		
		if(!this.usePlaceholder) {
			setValuePrefix(propertyMap.get("valueprefix"));
			setValueSuffix(propertyMap.get("valuesuffix"));
		}
	}
	private void setValuePrefix(String valuePrefix) {
		if(StringUtil.isEmpty(valuePrefix)) {
			if(dbDataType.isCharacterType()) {
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
			if(dbDataType.isCharacterType()) {
				this.valueSuffix = "'";
			}else {
				this.valueSuffix = "";
			}
		}else {
			this.valueSuffix = valueSuffix;
		}
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
	private void setDescription(String description) {
		if(StringUtil.isEmpty(description)) 
			description = name;
		this.description = description;
	}
	
	private void setValidateHandler() {
		ValidateHandler validateHandler = MappingParserContext.getSqlValidateHandlers().get(name);
		if(validateHandler == null && validate) {
			validateHandler = new ValidateHandler(name);
		}
		if(validateHandler != null) {
			this.validate = true;
			this.validateHandler = validateHandler;
			this.validateHandler.setNullableValidator(defaultValue==null?nullable:true);
			this.validateHandler.addValidator(new _DataTypeValidator(dbDataType, length, precision));
		}
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
		return getValue_(sqlParameter, null);
	}
	
	/**
	 * 获取值
	 * @param sqlParameter
	 * @param sqlParameterNamePrefix 即如果是alias.xxx, 要去除alias.
	 * @return
	 */
	public Object getValue(Object sqlParameter, String sqlParameterNamePrefix) {
		return getValue_(sqlParameter, sqlParameterNamePrefix);
	}
	
	/**
	 * 验证数据
	 * @param sqlParameter
	 * @param sqlParameterNamePrefix
	 * @return
	 */
	public ValidationResult validate(Object sqlParameter, String sqlParameterNamePrefix) {
		if(validate) 
			return validateHandler.validate(getValue_(sqlParameter, sqlParameterNamePrefix));
		return null;
	}
	
	public String getConfigText() {
		return configText;
	}
	public String getName() {
		return name;
	}
	public DBDataType getDBDataType() {
		return dbDataType;
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
	public int getLength() {
		return length;
	}
	public int getPrecision() {
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
	public String getDescription() {
		return description;
	}
	public SqlParameterConfigHolder getConfigHolder() {
		return configHolder;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		return name.equals(((SqlParameterMetadata) obj).getName());
	}

	@Override
	public String toString() {
		return "SqlParameterMetadata [name=" + name + ", dbDataType=" + dbDataType + "]";
	}
}
