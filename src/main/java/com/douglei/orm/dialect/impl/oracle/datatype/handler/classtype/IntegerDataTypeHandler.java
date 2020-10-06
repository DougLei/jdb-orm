package com.douglei.orm.dialect.impl.oracle.datatype.handler.classtype;

import com.douglei.orm.dialect.datatype.DBDataType;
import com.douglei.orm.dialect.impl.oracle.datatype.db.Number;
import com.douglei.orm.dialect.temp.datatype.handler.classtype.AbstractIntegerDataTypeHandler;

/**
 * 
 * @author DougLei
 */
public class IntegerDataTypeHandler extends AbstractIntegerDataTypeHandler{
	private static final long serialVersionUID = -4005742385107000513L;
	private IntegerDataTypeHandler() {}
	private static final IntegerDataTypeHandler instance = new IntegerDataTypeHandler();
	public static final IntegerDataTypeHandler singleInstance() {
		return instance;
	}
	
	@Override
	public DBDataType getDBDataType() {
		return Number.singleInstance();
	}
}
