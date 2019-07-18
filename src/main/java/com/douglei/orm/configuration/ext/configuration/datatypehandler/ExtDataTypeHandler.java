package com.douglei.orm.configuration.ext.configuration.datatypehandler;

import com.douglei.orm.core.dialect.datatype.handler.DataTypeHandlerType;
import com.douglei.orm.core.dialect.datatype.handler.classtype.ClassDataTypeHandler;
import com.douglei.orm.core.dialect.datatype.handler.dbtype.DBDataTypeHandler;
import com.douglei.orm.core.dialect.datatype.handler.resultset.columntype.ResultSetColumnDataTypeHandler;

/**
 * 扩展的DataTypeHandler
 * @author DougLei
 */
public interface ExtDataTypeHandler {

	DataTypeHandlerType getType();

	ClassDataTypeHandler getClassDataTypeHandler();

	ResultSetColumnDataTypeHandler getResultsetColumnDataTypeHandler();

	DBDataTypeHandler getDBDataTypeHandler();
}
