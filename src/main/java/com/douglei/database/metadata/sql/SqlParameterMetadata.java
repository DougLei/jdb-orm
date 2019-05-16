package com.douglei.database.metadata.sql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.configuration.LocalConfigurationData;
import com.douglei.database.dialect.datatype.DataTypeHandler;
import com.douglei.database.metadata.Metadata;
import com.douglei.database.metadata.MetadataType;

/**
 * sql参数元数据
 * @author DougLei
 */
public class SqlParameterMetadata implements Metadata{
	private static final Logger logger = LoggerFactory.getLogger(SqlParameterMetadata.class);
	
	/**
	 * 参数名
	 */
	private String name;
	/**
	 * 数据类型
	 */
	private DataTypeHandler dataTypeHandler;
	
	/**
	 * 是否使用占位符?
	 */
	private boolean usePlaceholder = true;
	/**
	 * 如果不使用占位符, 参数值的前缀
	 */
	private String placeholderPrefix = "'";
	/**
	 * 如果不使用占位符, 参数值的后缀
	 */
	private String placeholderSuffix = "'";
	
	
	public SqlParameterMetadata(String configurationString) {
		String[] cs = configurationString.split(",");
		int length = cs.length;
		
		if(length < 2) {
			throw new MatchingSqlParameterException("sql参数, 必须配置参数名和参数类型");
		}
		this.name = cs[0].trim().toUpperCase();
		this.dataTypeHandler = LocalConfigurationData.getDialect().getDataTypeHandlerMapping().getDataTypeHandlerByCode(getValue(cs[1]));
		
		if(length > 2) {
			logger.debug("设置usePlaceholder配置值");
			this.usePlaceholder = Boolean.parseBoolean(getValue(cs[2]));
			
			if(length > 3) {
				logger.debug("设置placeholderPrefix配置值");
				this.placeholderPrefix = getValue(cs[3]);
				
				if(length > 4) {
					logger.debug("设置placeholderSuffix配置值");
					this.placeholderSuffix = getValue(cs[4]);
				}
			}
		}
	}
	
	private String getValue(String key_value) {
		logger.debug("从 {} 中获取value值", key_value);
		String value = key_value.substring(key_value.indexOf("=")+1).trim();
		return value;
	}

	public String getName() {
		return name;
	}
	public DataTypeHandler getDataTypeHandler() {
		return dataTypeHandler;
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

	@Override
	public String getCode() {
		return name;
	}
	
	@Override
	public MetadataType getMetadataType() {
		return MetadataType.SQL_PARAMETER;
	}
}
