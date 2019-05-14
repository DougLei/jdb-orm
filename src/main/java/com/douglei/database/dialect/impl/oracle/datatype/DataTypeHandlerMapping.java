package com.douglei.database.dialect.impl.oracle.datatype;

import com.douglei.database.dialect.datatype.AbstractDataTypeHandlerMapping;
import com.douglei.database.dialect.impl.oracle.datatype.classtype.StringClassDataTypeHandler;

/**
 * oracle datatype handler mapping
 * @author DougLei
 */
public class DataTypeHandlerMapping extends AbstractDataTypeHandlerMapping {

	@Override
	protected void initialRegisterClassDataTypeHandlers() {
		registerClassDataTypeHandler(new StringClassDataTypeHandler());
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
