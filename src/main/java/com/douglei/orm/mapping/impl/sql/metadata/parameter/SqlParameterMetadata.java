package com.douglei.orm.mapping.impl.sql.metadata.parameter;

import java.util.Map;

import com.douglei.orm.configuration.environment.EnvironmentContext;
import com.douglei.orm.dialect.datatype.db.DBDataType;
import com.douglei.orm.mapping.metadata.Metadata;
import com.douglei.orm.mapping.validator.ValidateFailResult;
import com.douglei.tools.OgnlUtil;
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
	private String dbDataType;// 数据类型
	private transient DBDataType DBDataType; // 数据类型
	private int length;// 长度
	private int precision;// 精度
	
	private boolean nullable;// 是否可为空
	private String defaultValue;// 默认值
	
	private boolean placeholder;// 是否使用占位符?
	private String prefix;// 非占位符时的前缀字符
	private String suffix;// 非占位符时的后缀字符
	
	public SqlParameterMetadata(String configText) {
		this.configText = configText;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setMode(Mode mode) {
		this.mode = mode;
	}
	public void setDBDataType(DBDataType DBDataType) {
		this.dbDataType = DBDataType.getName();
		this.DBDataType = DBDataType;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public void setPrecision(int precision) {
		this.precision = precision;
	}
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	public void setNullable(boolean nullable) {
		this.nullable = nullable;
	}
	public void setPlaceholder(boolean placeholder) {
		this.placeholder = placeholder;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	public void setSuffix(String suffix) {
		this.suffix = suffix;
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
	public ValidateFailResult validate(Object sqlParameter, String alias) {
		if(validate) 
			return validateHandler.validate(getValue_(sqlParameter, alias));
		return null;
	}
	
	public String getConfigText() {
		return configText;
	}
	public Mode getMode() {
		return mode;
	}
	public String getName() {
		return name;
	}
	public DBDataType getDBDataType() {
		if(DBDataType == null)
			DBDataType = EnvironmentContext.getEnvironment().getDialect().getDataTypeContainer().getDBDataTypeByName(dbDataType);
		return DBDataType;
	}
	public int getLength() {
		return length;
	}
	public int getPrecision() {
		return precision;
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
	public String getDefaultValue() {
		return defaultValue;
	}
	public boolean isNullable() {
		return nullable;
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
