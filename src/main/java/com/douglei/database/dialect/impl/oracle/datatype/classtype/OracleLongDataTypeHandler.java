package com.douglei.database.dialect.impl.oracle.datatype.classtype;

import com.douglei.database.dialect.datatype.classtype.impl.LongDataTypeHandler;
import com.douglei.database.dialect.impl.oracle.datatype.OracleDBType;

/**
 * 
 * @author DougLei
 */
public class OracleLongDataTypeHandler extends LongDataTypeHandler{
	private OracleLongDataTypeHandler() {}
	private static final OracleLongDataTypeHandler instance = new OracleLongDataTypeHandler();
	public static final OracleLongDataTypeHandler singleInstance() {
		return instance;
	}
	
	@Override
	protected int getSqlType() {
		return OracleDBType.NUMBER.getSqlType();
	}
}
