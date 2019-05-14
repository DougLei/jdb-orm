package com.douglei.database.dialect.impl.mysql.datatype;

import com.douglei.database.dialect.datatype.AbstractDataTypeHandlerMapping;

/**
 * mysql datatype handler mapping
 * @author DougLei
 */
public class DataTypeHandlerMapping extends AbstractDataTypeHandlerMapping {
	
	@Override
	protected String getResultsetColumnDataTypeHandlerBasePackage() {
		return "com.douglei.database.dialect.impl.mysql.datatype.resultset.columntype";
	}
}
