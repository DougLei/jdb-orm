package com.douglei.database.dialect.impl.oracle;

import com.douglei.database.dialect.datatype.AbstractDataTypeHandlerMapping;

/**
 * oracle datatype handler mapping
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
