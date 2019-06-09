package com.douglei.orm.configuration.extconfiguration.datatypehandler;

import com.douglei.orm.core.dialect.Dialect;
import com.douglei.orm.core.dialect.datatype.handler.DataTypeHandlerType;
import com.douglei.orm.core.dialect.datatype.handler.classtype.ClassDataTypeHandler;
import com.douglei.orm.core.dialect.datatype.handler.dbtype.DBDataTypeHandler;
import com.douglei.orm.core.dialect.datatype.handler.resultset.columntype.ResultSetColumnDataTypeHandler;

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
