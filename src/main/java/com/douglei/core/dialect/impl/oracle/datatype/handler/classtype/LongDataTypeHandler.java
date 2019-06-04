package com.douglei.core.dialect.impl.oracle.datatype.handler.classtype;

import com.douglei.core.dialect.datatype.DBDataType;
import com.douglei.core.dialect.datatype.handler.classtype.AbstractLongDataTypeHandler;
import com.douglei.core.dialect.impl.oracle.datatype.Number;

/**
 * 
 * @author DougLei
 */
public class LongDataTypeHandler extends AbstractLongDataTypeHandler{
	private LongDataTypeHandler() {}
	private static final LongDataTypeHandler instance = new LongDataTypeHandler();
	public static final LongDataTypeHandler singleInstance() {
		return instance;
	}

	@Override
	public DBDataType defaultDBDataType() {
		return Number.singleInstance();
	}
}