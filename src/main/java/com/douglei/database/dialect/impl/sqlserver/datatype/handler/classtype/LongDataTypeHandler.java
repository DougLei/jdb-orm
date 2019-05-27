package com.douglei.database.dialect.impl.sqlserver.datatype.handler.classtype;

import com.douglei.database.dialect.datatype.handler.classtype.AbstractLongDataTypeHandler;
import com.douglei.database.dialect.impl.sqlserver.datatype.handler.SqlServerDBType;

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
		return SqlServerDBType.BIGINT.getSqlType();
	}
}
