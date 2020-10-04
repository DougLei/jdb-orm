package com.douglei.orm.dialect.impl.oracle.datatype.handler.classtype;

import com.douglei.orm.dialect.datatype.DBDataType;
import com.douglei.orm.dialect.datatype.handler.classtype.AbstractFloatDataTypeHandler;
import com.douglei.orm.dialect.impl.oracle.datatype.Number;

/**
 * 
 * @author DougLei
 */
public class FloatDataTypeHandler extends AbstractFloatDataTypeHandler {
	private static final long serialVersionUID = -2782956499219029407L;
	private FloatDataTypeHandler() {}
	private static final FloatDataTypeHandler instance = new FloatDataTypeHandler();
	public static final FloatDataTypeHandler singleInstance() {
		return instance;
	}

	@Override
	public DBDataType getDBDataType() {
		return Number.singleInstance();
	}
}
