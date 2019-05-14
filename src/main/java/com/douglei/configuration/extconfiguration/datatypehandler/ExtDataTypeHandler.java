package com.douglei.configuration.extconfiguration.datatypehandler;

import com.douglei.database.dialect.Dialect;
import com.douglei.database.dialect.datatype.DataTypeHandlerType;
import com.douglei.database.dialect.datatype.classtype.ClassDataTypeHandler;
import com.douglei.database.dialect.datatype.resultset.columntype.ResultSetColumnDataTypeHandler;

/**
 * 扩展的DataTypeHandler
 * @author DougLei
 */
public interface ExtDataTypeHandler {

	Dialect getDialect();
	
	DataTypeHandlerType getType();

	ClassDataTypeHandler getClassDataTypeHandler();

	ResultSetColumnDataTypeHandler getResultsetColumnDataTypeHandler();
}
