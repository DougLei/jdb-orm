package com.douglei.database.dialect.impl.oracle.datatype.handler.classtype;

import com.douglei.database.dialect.datatype.handler.classtype.AbstractClobDataTypeHandler;
import com.douglei.database.dialect.impl.oracle.datatype.handler.OracleDBType;

/**
 * 
 * @author DougLei
 */
public class ClobDataTypeHandler extends AbstractClobDataTypeHandler{
	private ClobDataTypeHandler() {}
	private static final ClobDataTypeHandler instance = new ClobDataTypeHandler();
	public static final ClobDataTypeHandler singleInstance() {
		return instance;
	}
	
	@Override
	protected int getSqlType() {
		return OracleDBType.CLOB.getSqlType();
	}
}
