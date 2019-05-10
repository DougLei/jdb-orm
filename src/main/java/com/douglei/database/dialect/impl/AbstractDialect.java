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
	public DataTypeHandler getDataTypeHandlerByCode(String code) {
		logger.debug("获得code={}的DataTypeHandler", code);
		DataTypeHandler dataTypeHandler = dataTypeHandlerMapping.getDataTypeHandlerByCode(code);
		if(logger.isDebugEnabled()) {
			logger.debug("获取code={}的 {}实例", code, dataTypeHandler.getClass().getName());
		}
		return dataTypeHandler;
	}
}
