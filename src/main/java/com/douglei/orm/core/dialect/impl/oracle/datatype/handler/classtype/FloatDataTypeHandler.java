package com.douglei.orm.core.dialect.impl.oracle.datatype.handler.classtype;

import com.douglei.orm.core.dialect.datatype.DBDataType;
import com.douglei.orm.core.dialect.datatype.handler.classtype.AbstractFloatDataTypeHandler;
import com.douglei.orm.core.dialect.impl.oracle.datatype.Number;

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
		return Number.singleInstance();
	}
}
