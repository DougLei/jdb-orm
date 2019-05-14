package com.douglei.database.dialect.impl.sqlserver.datatype;

import com.douglei.database.dialect.datatype.AbstractDataTypeHandlerMapping;

/**
 * sqlserver datatype handler mapping
 * @author DougLei
 */
public class DataTypeHandlerMapping extends AbstractDataTypeHandlerMapping {

	@Override
	protected String[] dataTypeHandlerBasePackages() {
		return new String[] {"com.douglei.database.dialect.impl.sqlserver.datatype.classtype", 
				"com.douglei.database.dialect.impl.sqlserver.datatype.resultset.columntype"};
	}
}
