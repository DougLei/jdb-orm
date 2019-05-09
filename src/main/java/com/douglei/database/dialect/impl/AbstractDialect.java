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
	
	private static AbstractDataTypeHandlerMapping dataTypeHandlerMapping;
	protected static void setDataTypeHandlerMapping(AbstractDataTypeHandlerMapping mapping) {
		dataTypeHandlerMapping = mapping;
	}
	
	
	@Override
	public DataTypeHandler getDataTypeHandler(String code) {
		logger.debug("获得code={}的DataTypeHandler", code);
		DataTypeHandler dataTypeHandler = dataTypeHandlerMapping.getDataTypeHandler(code);
		if(logger.isDebugEnabled()) {
			logger.debug("获取code={}的 {}实例", code, dataTypeHandler.getClass().getName());
		}
		return dataTypeHandler;
	}

	@Override
	public DataTypeHandler getDataTypeHandler(Object value) {
		logger.debug("获得object value={}的DataTypeHandler", value);
		DataTypeHandler dataTypeHandler = dataTypeHandlerMapping.getDataTypeHandler(value);
		if(logger.isDebugEnabled()) {
			logger.debug("获取object value={}的 {}实例", value, dataTypeHandler.getClass().getName());
		}
		return dataTypeHandler;
	}
}
