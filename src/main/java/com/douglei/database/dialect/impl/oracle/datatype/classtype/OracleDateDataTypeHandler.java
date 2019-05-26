package com.douglei.database.dialect.impl.oracle.datatype.classtype;

import com.douglei.database.dialect.datatype.classtype.impl.DateDataTypeHandler;
import com.douglei.database.dialect.impl.oracle.datatype.OracleDBType;

/**
 * 
 * @author DougLei
 */
public class OracleDateDataTypeHandler extends DateDataTypeHandler{
	private OracleDateDataTypeHandler() {}
	private static final OracleDateDataTypeHandler instance = new OracleDateDataTypeHandler();
	public static final OracleDateDataTypeHandler singleInstance() {
		return instance;
	}
	
	@Override
	protected int getSqlType() {
		return OracleDBType.DATE.getSqlType();
	}
}
