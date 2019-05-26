package com.douglei.database.dialect.impl.oracle.datatype.classtype;

import com.douglei.database.dialect.datatype.classtype.impl.ClobDataTypeHandler;
import com.douglei.database.dialect.impl.oracle.datatype.OracleDBType;

/**
 * 
 * @author DougLei
 */
public class OracleClobDataTypeHandler extends ClobDataTypeHandler{
	private OracleClobDataTypeHandler() {}
	private static final OracleClobDataTypeHandler instance = new OracleClobDataTypeHandler();
	public static final OracleClobDataTypeHandler singleInstance() {
		return instance;
	}
	
	@Override
	protected int getSqlType() {
		return OracleDBType.CLOB.getSqlType();
	}
}
