package com.douglei.database.dialect.impl.oracle.datatype.classtype;

import com.douglei.database.dialect.datatype.classtype.impl.DoubleDataTypeHandler;
import com.douglei.database.dialect.impl.oracle.datatype.OracleDBType;

/**
 * 
 * @author DougLei
 */
public class OracleDoubleDataTypeHandler extends DoubleDataTypeHandler{
	private OracleDoubleDataTypeHandler() {}
	private static final OracleDoubleDataTypeHandler instance = new OracleDoubleDataTypeHandler();
	public static final OracleDoubleDataTypeHandler singleInstance() {
		return instance;
	}
	
	@Override
	protected int getSqlType() {
		return OracleDBType.NUMBER.getSqlType();
	}
}
