package com.douglei.database.dialect.impl.sqlserver.datatype;

import com.douglei.database.dialect.datatype.AbstractDataTypeHandlerMapping;

/**
 * sqlserver datatype handler mapping
 * @author DougLei
 */
public class DataTypeHandlerMapping extends AbstractDataTypeHandlerMapping {

	@Override
	protected String getResultsetColumnDataTypeHandlerBasePackage() {
		return "com.douglei.database.dialect.impl.sqlserver.datatype.resultset.columntype";
	}
}
