package com.douglei.database.dialect.impl.oracle.datatype.classtype;

import com.douglei.database.dialect.datatype.classtype.impl.IntegerDataTypeHandler;
import com.douglei.database.dialect.impl.oracle.datatype.OracleDBType;

/**
 * 
 * @author DougLei
 */
public class OracleIntegerDataTypeHandler extends IntegerDataTypeHandler{
	private OracleIntegerDataTypeHandler() {}
	private static final OracleIntegerDataTypeHandler instance = new OracleIntegerDataTypeHandler();
	public static final OracleIntegerDataTypeHandler singleInstance() {
		return instance;
	}
	
	@Override
	protected int getSqlType() {
		return OracleDBType.NUMBER.getSqlType();
	}
}
