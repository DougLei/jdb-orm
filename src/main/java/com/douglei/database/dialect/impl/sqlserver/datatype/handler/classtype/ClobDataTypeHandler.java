package com.douglei.database.dialect.impl.sqlserver.datatype.handler.classtype;

import com.douglei.database.dialect.datatype.handler.classtype.AbstractClobDataTypeHandler;
import com.douglei.database.dialect.impl.sqlserver.datatype.handler.SqlServerDBType;

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
		return SqlServerDBType.TEXT.getSqlType();
	}
}
