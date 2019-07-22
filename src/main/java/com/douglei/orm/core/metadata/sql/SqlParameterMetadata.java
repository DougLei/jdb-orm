package com.douglei.orm.core.metadata.sql;

import java.util.Map;

import com.douglei.orm.core.dialect.db.sql.entity.AbstractSqlParameter;
import com.douglei.orm.core.metadata.Metadata;
import com.douglei.orm.core.metadata.MetadataType;
import com.douglei.orm.core.validate.ValidateException;
import com.douglei.tools.instances.ognl.OgnlHandler;
import com.douglei.tools.utils.datatype.converter.ConverterUtil;

/**
 * sql参数元数据
 * @author DougLei
 */
public class SqlParameterMetadata extends AbstractSqlParameter implements Metadata{
	private static final long serialVersionUID = -2141160768098739859L;
	
	public SqlParameterMetadata(String configurationText) {
		super(configurationText);
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
		return "SqlParameterMetadata [configurationText=" + configurationText + ", propertyMap=" + propertyMap
				+ ", name=" + name + ", dataType=" + dataType + ", mode=" + mode + ", usePlaceholder=" + usePlaceholder
				+ ", valuePrefix=" + valuePrefix + ", valueSuffix=" + valueSuffix + ", nullabled=" + nullabled
				+ ", defaultValue=" + defaultValue + ", validate=" + validate + "]";
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
		}else if(ConverterUtil.isSimpleType(sqlParameter)){
			value = sqlParameter;
		}else {
			value = OgnlHandler.singleInstance().getObjectValue(name, sqlParameter);
		}
		
		if(value == null) {
			value = defaultValue;
		}
		doValidate(value);
		return value;
	}
	
	// 验证数据
	private void doValidate(Object value) {
		if(validate) {
			if(!nullabled && value == null) {
				throw new ValidateException(descriptionName, name, "不能为空");
			}
			if(value != null) {
				String result = dataType.doValidate(value, length, precision);
				if(result != null) {
					throw new ValidateException(descriptionName, name, result);
				}
			}
		}
	}
}
