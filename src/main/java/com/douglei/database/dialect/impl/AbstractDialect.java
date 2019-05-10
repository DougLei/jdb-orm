package com.douglei.database.dialect.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.douglei.database.dialect.Dialect;
import com.douglei.database.dialect.datatype.AbstractDataTypeHandlerMapping;
import com.douglei.database.dialect.datatype.DataTypeHandler;

/**
 * 
 * @author DougLei
 */
public abstract class AbstractDialect implements Dialect{
	private static final Logger logger = LoggerFactory.getLogger(AbstractDialect.class);
	
	
	// DataTypeHandlerMapping, 各个子类的实现
	private static AbstractDataTypeHandlerMapping dataTypeHandlerMapping;
	protected static void setDataTypeHandlerMapping(AbstractDataTypeHandlerMapping mapping) {
		dataTypeHandlerMapping = mapping;
	}
	
	@Override
	public DataTypeHandler getDataTypeHandlerByCode(String code) {
		DataTypeHandler dataTypeHandler = dataTypeHandlerMapping.getDataTypeHandlerByCode(code);
		if(logger.isDebugEnabled()) {
			logger.debug("获取code={}的 {}实例", code, dataTypeHandler.getClass().getName());
		}
		return dataTypeHandler;
	}
	
	@Override
	public DataTypeHandler getDataTypeHandlerByValueClassType(Object value) {
		DataTypeHandler dataTypeHandler = dataTypeHandlerMapping.getDataTypeHandlerByValueClassType(value);
		if(logger.isDebugEnabled()) {
			logger.debug("获取value={}, valueClassType={}的 {}实例", value, value.getClass().getName(), dataTypeHandler.getClass().getName());
		}
		return dataTypeHandler;
	}
	
	@Override
	public DataTypeHandler getDataTypeHandlerByDatabaseColumnType(int columnType) {
		DataTypeHandler dataTypeHandler = dataTypeHandlerMapping.getDataTypeHandlerByDatabaseColumnType(columnType);
		if(logger.isDebugEnabled()) {
			logger.debug("获取columnType={}, {}实例", columnType, dataTypeHandler.getClass().getName());
		}
		return dataTypeHandler;
	}
}
