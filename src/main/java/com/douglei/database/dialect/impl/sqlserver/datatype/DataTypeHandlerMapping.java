package com.douglei.database.dialect.impl.sqlserver.datatype;

import com.douglei.database.dialect.datatype.AbstractDataTypeHandlerMapping;

/**
 * sqlserver datatype handler mapping
 * @author DougLei
 */
public class DataTypeHandlerMapping extends AbstractDataTypeHandlerMapping {

	@Override
	protected void initialRegisterClassDataTypeHandlers() {
		// TODO 
//		registerClassDataTypeHandler(classDataHandler);
	}
	
	@Override
	protected void initialRegisterOrmDataTypeHandlers() {
		// TODO
//		registerOrmDataTypeHandler(ormDataTypeHandler);
		
	}

	@Override
	protected void initialRegisterResultsetColumnDataTypeHandlers() {
		// TODO
//		registerResultsetColumnDataTypeHandler(resultSetColumnDatatTypeHandler);
		
	}
}
