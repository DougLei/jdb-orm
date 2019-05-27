package com.douglei.database.dialect.impl.oracle.datatype.handler.classtype;

import com.douglei.database.dialect.datatype.handler.classtype.AbstractIntegerDataTypeHandler;
import com.douglei.database.dialect.impl.oracle.datatype.handler.OracleDBType;

/**
 * 
 * @author DougLei
 */
public class IntegerDataTypeHandler extends AbstractIntegerDataTypeHandler{
	private IntegerDataTypeHandler() {}
	private static final IntegerDataTypeHandler instance = new IntegerDataTypeHandler();
	public static final IntegerDataTypeHandler singleInstance() {
		return instance;
	}
	
	@Override
	protected int getSqlType() {
		return OracleDBType.NUMBER.getSqlType();
	}
}
