package com.douglei.orm.core.metadata.sql;

import java.util.Map;

import com.douglei.orm.core.dialect.db.sql.entity.AbstractSqlParameter;
import com.douglei.orm.core.metadata.Metadata;
import com.douglei.orm.core.metadata.MetadataType;
import com.douglei.tools.instances.ognl.OgnlHandler;
import com.douglei.tools.utils.datatype.ValidationUtil;

/**
 * sql参数元数据
 * @author DougLei
 */
public class SqlParameterMetadata extends AbstractSqlParameter implements Metadata{
	
	public SqlParameterMetadata(String configurationText) {
		super(configurationText, true);
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
		return "AbstractSqlParameter [configurationText=" + configurationText + ", propertyMap=" + propertyMap
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
		}else if(ValidationUtil.isBasicDataType(sqlParameter)){
			value = sqlParameter;
		}else {
			value = OgnlHandler.singleInstance().getObjectValue(name, sqlParameter);
		}
		return value;
	}
}
