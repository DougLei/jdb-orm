package com.douglei.database.dialect.impl.sqlserver;

import com.douglei.database.dialect.datatype.AbstractDataTypeHandlerMapping;

/**
 * sqlserver datatype handler mapping
 * @author DougLei
 */
class DataTypeHandlerMapping extends AbstractDataTypeHandlerMapping {
	static {
		
	}
	
	private DataTypeHandlerMapping() {}
	private static final DataTypeHandlerMapping instance =new DataTypeHandlerMapping();
	public static final DataTypeHandlerMapping singleInstance() {
		return instance;
	}
}
