package com.douglei.configuration.extconfiguration.datatypehandler;

import com.douglei.core.dialect.Dialect;
import com.douglei.core.dialect.datatype.handler.DataTypeHandlerType;
import com.douglei.core.dialect.datatype.handler.classtype.ClassDataTypeHandler;
import com.douglei.core.dialect.datatype.handler.dbtype.DBDataTypeHandler;
import com.douglei.core.dialect.datatype.handler.resultset.columntype.ResultSetColumnDataTypeHandler;

/**
 * 扩展的DataTypeHandler
 * @author DougLei
 */
public interface ExtDataTypeHandler {

	Dialect getDialect();
	
	DataTypeHandlerType getType();

	ClassDataTypeHandler getClassDataTypeHandler();

	ResultSetColumnDataTypeHandler getResultsetColumnDataTypeHandler();

	DBDataTypeHandler getDBDataTypeHandler();
}
