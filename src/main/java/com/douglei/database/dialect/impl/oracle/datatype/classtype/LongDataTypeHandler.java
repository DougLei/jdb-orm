package com.douglei.database.dialect.impl.oracle.datatype.classtype;

import com.douglei.database.dialect.datatype.classtype.impl.AbstractLongDataTypeHandler;
import com.douglei.database.dialect.impl.oracle.datatype.OracleDBType;

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
