package com.douglei.database.dialect.impl.oracle.datatype.handler.classtype;

import com.douglei.database.dialect.datatype.handler.classtype.AbstractLongDataTypeHandler;
import com.douglei.database.dialect.impl.oracle.datatype.handler.OracleDBType;

/**
 * 
 * @author DougLei
 */
public class LongDataTypeHandler extends AbstractLongDataTypeHandler{
	private LongDataTypeHandler() {}
	private static final LongDataTypeHandler instance = new LongDataTypeHandler();
	public static final LongDataTypeHandler singleInstance() {
		return instance;
	}
	
	@Override
	protected int getSqlType() {
		return OracleDBType.NUMBER.getSqlType();
	}
}
