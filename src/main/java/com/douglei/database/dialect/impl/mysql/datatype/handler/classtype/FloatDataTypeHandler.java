package com.douglei.database.dialect.impl.mysql.datatype.handler.classtype;

import com.douglei.database.dialect.datatype.DBDataType;
import com.douglei.database.dialect.datatype.handler.classtype.AbstractFloatDataTypeHandler;
import com.douglei.database.dialect.impl.mysql.datatype.Decimal;

/**
 * 
 * @author DougLei
 */
public class FloatDataTypeHandler extends AbstractFloatDataTypeHandler{
	private FloatDataTypeHandler() {}
	private static final FloatDataTypeHandler instance = new FloatDataTypeHandler();
	public static final FloatDataTypeHandler singleInstance() {
		return instance;
	}
	
	@Override
	public DBDataType defaultDBDataType() {
		return Decimal.singleInstance();
	}
}
