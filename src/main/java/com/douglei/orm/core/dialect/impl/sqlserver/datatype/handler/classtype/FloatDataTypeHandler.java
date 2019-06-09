package com.douglei.orm.core.dialect.impl.sqlserver.datatype.handler.classtype;

import com.douglei.orm.core.dialect.datatype.DBDataType;
import com.douglei.orm.core.dialect.datatype.handler.classtype.AbstractFloatDataTypeHandler;
import com.douglei.orm.core.dialect.impl.sqlserver.datatype.Decimal;

/**
 * 
 * @author DougLei
 */
public class FloatDataTypeHandler extends AbstractFloatDataTypeHandler {
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
