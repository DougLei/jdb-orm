package com.douglei.configuration.extconfiguration.datatypehandler;

import com.douglei.database.dialect.Dialect;
import com.douglei.database.dialect.datatype.handler.DataTypeHandlerType;
import com.douglei.database.dialect.datatype.handler.classtype.ClassDataTypeHandler;
import com.douglei.database.dialect.datatype.handler.dbtype.DBDataTypeHandler;
import com.douglei.database.dialect.datatype.handler.resultset.columntype.ResultSetColumnDataTypeHandler;

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
