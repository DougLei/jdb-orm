package com.douglei.database.dialect.impl.sqlserver;

import com.douglei.database.dialect.datatype.AbstractDataTypeHandlerMapping;
import com.douglei.database.dialect.datatype.DataTypeHandler;

/**
 * sqlserver datatype handler mapping
 * @author DougLei
 */
class DataTypeHandlerMapping extends AbstractDataTypeHandlerMapping {
	static {
		
	}
	
	@Override
	public DataTypeHandler getDataTypeHandler(Object value) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private DataTypeHandlerMapping() {}
	private static final DataTypeHandlerMapping instance =new DataTypeHandlerMapping();
	public static final DataTypeHandlerMapping singleInstance() {
		return instance;
	}
}
