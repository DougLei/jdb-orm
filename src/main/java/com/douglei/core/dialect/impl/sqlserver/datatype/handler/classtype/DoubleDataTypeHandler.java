package com.douglei.core.dialect.impl.sqlserver.datatype.handler.classtype;

import com.douglei.core.dialect.datatype.DBDataType;
import com.douglei.core.dialect.datatype.handler.classtype.AbstractDoubleDataTypeHandler;
import com.douglei.core.dialect.impl.sqlserver.datatype.Decimal;

/**
 * 
 * @author DougLei
 */
public class DoubleDataTypeHandler extends AbstractDoubleDataTypeHandler{
	private DoubleDataTypeHandler() {}
	private static final DoubleDataTypeHandler instance = new DoubleDataTypeHandler();
	public static final DoubleDataTypeHandler singleInstance() {
		return instance;
	}
	
	@Override
	public DBDataType defaultDBDataType() {
		return Decimal.singleInstance();
	}
}