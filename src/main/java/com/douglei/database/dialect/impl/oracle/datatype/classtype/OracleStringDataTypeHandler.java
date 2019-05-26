package com.douglei.database.dialect.impl.oracle.datatype.classtype;

import com.douglei.database.dialect.datatype.classtype.impl.StringDataTypeHandler;
import com.douglei.database.dialect.impl.oracle.datatype.OracleDBType;

/**
 * 
 * @author DougLei
 */
public class OracleStringDataTypeHandler extends StringDataTypeHandler{
	private OracleStringDataTypeHandler() {}
	private static final OracleStringDataTypeHandler instance = new OracleStringDataTypeHandler();
	public static final OracleStringDataTypeHandler singleInstance() {
		return instance;
	}
	
	@Override
	protected int getSqlType() {
		return OracleDBType.VARCHAR2.getSqlType();
	}
}
