package com.douglei.orm.mapping.impl.sql.metadata.parameter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.douglei.orm.dialect.datatype.DataTypeClassification;
import com.douglei.orm.dialect.datatype.db.DBDataType;
import com.douglei.orm.dialect.datatype.db.DBDataTypeEntity;
import com.douglei.orm.dialect.datatype.db.DBDataTypeUtil;
import com.douglei.orm.mapping.impl.MappingParserContext;
import com.douglei.orm.mapping.impl.sql.metadata.content.ContentType;
import com.douglei.orm.mapping.metadata.Metadata;
import com.douglei.orm.mapping.validator.Validator;
import com.douglei.orm.mapping.validator.ValidatorParser;
import com.douglei.tools.OgnlUtil;
import com.douglei.tools.RegularExpressionUtil;
import com.douglei.tools.StringUtil;
import com.douglei.tools.datatype.DataTypeConvertUtil;
import com.douglei.tools.reflect.IntrospectorUtil;

/**
 * 
 * @author DougLei
 */
public class SqlParameterMetadata implements Metadata{

	private transient String configText;
	
	private String name;// 参数名
	private Mode mode;// 输入输出类型
	private DBDataType dbDataType;// 数据类型
	private int length;// 长度
	private int precision;// 精度
	
	private boolean nullable;// 是否可为空
	private String defaultValue;// 默认值
	
	private boolean placeholder;// 是否使用占位符?
	private String prefix;// 如果不使用占位符, 参数值的前缀
	private String suffix;// 如果不使用占位符, 参数值的后缀
	
	private String description;// 描述
	
	public SqlParameterMetadata(String configText, String split) {
		// 设置配置的内容, 如果存在正则表达式的关键字, 则增加\转义
		this.configText = RegularExpressionUtil.includeKey(configText)?RegularExpressionUtil.addBackslash4Key(configText):configText;

		Map<String, String> propertyMap = resolvingPropertyMap(configText, split);
		
		setDBDataType(propertyMap);
		setPlaceholder(propertyMap);
		
		this.defaultValue = propertyMap.get("defaultvalue");
		this.nullable = (defaultValue == null)?!"false".equalsIgnoreCase(propertyMap.get("nullable")):true;
		this.description = propertyMap.get("description");
		
		propertyMap.clear();
	}
	
	// 解析出属性map集合
	private Map<String, String> resolvingPropertyMap(String configText, String split) {
		String[] cts = configText.split(split);
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
	
	private void setDBDataType(Map<String, String> propertyMap) {
		String typeName = propertyMap.get("dbtype");
		DataTypeClassification classification = DataTypeClassification.DB;
		
		if(MappingParserContext.getCurrentSqlType() == ContentType.PROCEDURE) {
			if(StringUtil.isEmpty(typeName))
				throw new NullPointerException("存储过程中, 参数["+name+"]的dbType不能为空");
			
			if(propertyMap.get("mode") == null) {
				this.mode = Mode.IN;
			}else {
				this.mode = Mode.valueOf(propertyMap.get("mode").toUpperCase());
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
		this.dbDataType = dataTypeEntity.getDBDataType();
		this.length = dataTypeEntity.getLength();
		this.precision = dataTypeEntity.getPrecision();
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
			this.prefix = dbDataType.isCharacterType()?"'":"";
		}else {
			this.prefix = prefix;
		}
	}
	private void setSuffix(String suffix) {
		if(StringUtil.isEmpty(suffix)) {
			this.suffix = dbDataType.isCharacterType()?"'":"";
		}else {
			this.suffix = suffix;
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
			}else if(DataTypeConvertUtil.isSimpleType(sqlParameter)){
				value = sqlParameter;
			}else {
				value = OgnlUtil.getSingleton().getObjectValue(name, sqlParameter);
			}
		}
		
		if(value == null && defaultValue != null) {
			value = configHolder.getDefaultValueHandler().getDefaultValue(defaultValue);
			
			if(singleName) {
				IntrospectorUtil.setProperyValue(sqlParameter, name, value);
			}else {
				int dot = name.lastIndexOf(".");
				IntrospectorUtil.setProperyValue(OgnlUtil.getSingleton().getObjectValue(name.substring(0, dot), sqlParameter), this.name.substring(dot+1), value);
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
	public Mode getMode() {
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
	public String getDescription() {
		return description;
	}
	
	@Override
	public final boolean equals(Object obj) {
		return name.equals(((SqlParameterMetadata) obj).name);
	}

	@Override
	public String toString() {
		return "SqlParameterMetadata [name=" + name + ", dbDataType=" + dbDataType + "]";
	}
}
