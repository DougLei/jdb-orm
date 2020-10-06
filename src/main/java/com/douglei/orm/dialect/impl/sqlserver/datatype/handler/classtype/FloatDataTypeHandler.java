package com.douglei.orm.dialect.impl.sqlserver.datatype.handler.classtype;

import com.douglei.orm.dialect.datatype.DBDataType;
import com.douglei.orm.dialect.impl.sqlserver.datatype.db.Decimal;
import com.douglei.orm.dialect.temp.datatype.handler.classtype.AbstractFloatDataTypeHandler;

/**
 * 
 * @author DougLei
 */
public class FloatDataTypeHandler extends AbstractFloatDataTypeHandler {
	private static final long serialVersionUID = -2640338557410322125L;
	private FloatDataTypeHandler() {}
	private static final FloatDataTypeHandler instance = new FloatDataTypeHandler();
	public static final FloatDataTypeHandler singleInstance() {
		return instance;
	}
	
	@Override
	public DBDataType getDBDataType() {
		return Decimal.singleInstance();
	}
}
