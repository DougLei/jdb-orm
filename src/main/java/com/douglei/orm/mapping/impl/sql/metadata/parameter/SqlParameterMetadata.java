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
import com.douglei.orm.mapping.metadata.validator.impl._NullableValidator;
import com.douglei.tools.instances.ognl.OgnlHandler;
import com.douglei.tools.utils.RegularExpressionUtil;
import com.douglei.tools.utils.StringUtil;
import com.douglei.tools.utils.datatype.converter.ConverterUtil;
import com.douglei.tools.utils.reflect.IntrospectorUtil;

/**
 * sql参数元数据
 * @author DougLei
 */
public class SqlParameterMetadata implements Metadata{
	private static final long serialVersionUID = 1648404942738838493L;

	private String configText;
	
	private String name;// 参数名
	private DBDataType dbDataType;// 数据类型
	private int length;// 长度
	private int precision;// 精度
	
	private SqlParameterMode mode;// 输入输出类型
	
	private boolean placeholder;// 是否使用占位符?
	private String prefix;// 如果不使用占位符, 参数值的前缀
	private String suffix;// 如果不使用占位符, 参数值的后缀
	
	private boolean nullable;// 是否可为空
	private String defaultValue;// 默认值
	private boolean validate;// 是否验证
	private String description;// 描述
	
	private ValidateHandler validateHandler;// 验证器
	
	private SqlParameterConfigHolder configHolder;
	
	public SqlParameterMetadata(String configText, SqlParameterConfigHolder sqlParameterConfigHolder) {
		// 设置配置的内容, 如果存在正则表达式的关键字, 则增加\转义
		this.configText = RegularExpressionUtil.includeKey(configText)?RegularExpressionUtil.transferKey(configText):configText;

		Map<String, String> propertyMap = resolvingPropertyMap(configText, sqlParameterConfigHolder);
		setDBDataType(propertyMap);
		
		setPlaceholder(propertyMap);
		setDefaultValueAndNullable(propertyMap.get("defaultvalue"), propertyMap.get("nullable"));
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
	
	private void setDBDataType(Map<String, String> propertyMap) {
		String type = propertyMap.get("dbtype");
		if(MappingParserContext.getCurrentSqlType() == ContentType.PROCEDURE) {
			if(StringUtil.isEmpty(type))
				throw new NullPointerException("存储过程中, 参数["+name+"]的dbType不能为空");
			
			this.mode = SqlParameterMode.toValue(propertyMap.get("mode"));
		} else {
			if(StringUtil.isEmpty(type)) {
				type = propertyMap.get("datatype");
				if(StringUtil.isEmpty(type))
					type = "string";
			}
		}
		
		// 这里不对dataType进行转大/小写的操作, 原因是, dataType可能是一个自定义类的全路径, 转换后无法进行反射构建实例
		DBDataTypeWrapper wrapper = DBDataTypeUtil.get(propertyMap.get("length"), propertyMap.get("precision"), type);
		this.dbDataType = wrapper.getDBDataType();
		this.length = wrapper.getLength();
		this.precision = wrapper.getPrecision();
	}
	private void setPlaceholder(Map<String, String> propertyMap) {
		this.placeholder = !"false".equalsIgnoreCase(propertyMap.get("placeholder"));
		if(!this.placeholder) {
			setPrefix(propertyMap.get("prefix"));
			setSuffix(propertyMap.get("suffix"));
		}
	}
	private void setPrefix(String prefix) {
		if(StringUtil.isEmpty(prefix)) {
			if(dbDataType.isCharacterType()) {
				this.prefix = "'";
			}else {
				this.prefix = "";
			}
		}else {
			this.prefix = prefix;
		}
	}
	private void setSuffix(String suffix) {
		if(StringUtil.isEmpty(suffix)) {
			if(dbDataType.isCharacterType()) {
				this.suffix = "'";
			}else {
				this.suffix = "";
			}
		}else {
			this.suffix = suffix;
		}
	}
	private void setDefaultValueAndNullable(String defaultValue, String nullable) {
		this.defaultValue = defaultValue;
		if(defaultValue == null) {
			this.nullable = StringUtil.isEmpty(nullable)?true:Boolean.parseBoolean(nullable);
		}else {
			this.nullable = true;
		}
	}
	private void setValidate(String validate) {
		this.validate = Boolean.parseBoolean(validate);
	}
	private void setDescription(String description) {
		this.description = StringUtil.isEmpty(description)?name:description;
	}
	
	private void setValidateHandler() {
		ValidateHandler validateHandler = MappingParserContext.getSqlValidateHandlers().get(name);
		if(validate && validateHandler == null) 
			validateHandler = new ValidateHandler(name);
		if(validateHandler != null) {
			this.validate = true;
			this.validateHandler = validateHandler;
			this.validateHandler.addValidator(new _NullableValidator(nullable));
			this.validateHandler.addValidator(new _DataTypeValidator(dbDataType, length, precision));
			this.validateHandler.sort();
		}
	}

	private boolean nameFlag;// 标识是否处理过name
	private boolean singleName;// 是否只是一个name, 如果不是的话(即alias.xxx这种多层级name), 则需要ognl解析
	// 获取值
	private Object getValue_(Object sqlParameter, String alias) {
		if(!nameFlag) {
			nameFlag = true;
			if(alias != null && name.startsWith(alias+'.'))
				name = name.substring(alias.length()+1);
			singleName = name.indexOf(".") == -1;// 可能会出现alias.xx.xx的多层级形式
		}
		
		Object value = null;
		if(sqlParameter != null) {
			if(sqlParameter instanceof Map<?, ?> && singleName) {
				value = ((Map<?, ?>)sqlParameter).get(name); 
			}else if(ConverterUtil.isSimpleType(sqlParameter)){
				value = sqlParameter;
			}else {
				value = OgnlHandler.getSingleton().getObjectValue(name, sqlParameter);
			}
		}
		
		if(value == null && defaultValue != null) {
			value = configHolder.getDefaultValueHandler().getDefaultValue(defaultValue);
			
			if(singleName) {
				IntrospectorUtil.setProperyValue(sqlParameter, name, value);
			}else {
				int dot = name.lastIndexOf(".");
				IntrospectorUtil.setProperyValue(OgnlHandler.getSingleton().getObjectValue(name.substring(0, dot), sqlParameter), this.name.substring(dot+1), value);
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
	 * @param alias 即如果是alias.xxx, 要去除alias.
	 * @return
	 */
	public Object getValue(Object sqlParameter, String alias) {
		return getValue_(sqlParameter, alias);
	}
	
	/**
	 * 验证数据
	 * @param sqlParameter
	 * @param alias
	 * @return
	 */
	public ValidationResult validate(Object sqlParameter, String alias) {
		if(validate) 
			return validateHandler.validate(getValue_(sqlParameter, alias));
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
	public boolean isPlaceholder() {
		return placeholder;
	}
	public String getPrefix() {
		return prefix;
	}
	public String getSuffix() {
		return suffix;
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
		return name.equals(((SqlParameterMetadata) obj).name);
	}

	@Override
	public String toString() {
		return "SqlParameterMetadata [name=" + name + ", dbDataType=" + dbDataType + "]";
	}
}
