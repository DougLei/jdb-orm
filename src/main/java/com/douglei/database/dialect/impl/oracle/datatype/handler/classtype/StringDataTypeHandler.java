package com.douglei.database.dialect.impl.oracle.datatype.handler.classtype;

import com.douglei.database.dialect.datatype.handler.classtype.AbstractStringDataTypeHandler;
import com.douglei.database.dialect.impl.oracle.datatype.handler.OracleDBType;

/**
 * 
 * @author DougLei
 */
public class StringDataTypeHandler extends AbstractStringDataTypeHandler{
	private StringDataTypeHandler() {}
	private static final StringDataTypeHandler instance = new StringDataTypeHandler();
	public static final StringDataTypeHandler singleInstance() {
		return instance;
	}
	
	@Override
	protected int getSqlType() {
		return OracleDBType.VARCHAR2.getSqlType();
	}
}
